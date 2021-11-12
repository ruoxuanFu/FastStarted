package com.fsdk.faststarted.widget.banner

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


//可无限滚动的banner，优化ViewPager2嵌套滑动冲突
//https://juejin.cn/post/6911456860533063688
class Banner(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var viewPager2: ViewPager2 = ViewPager2(context)
    private val compositePageTransformer = CompositePageTransformer()
    private lateinit var changeCallback: ViewPager2.OnPageChangeCallback
    private val viewPager2Adapter: BannerAdapterWrapper = BannerAdapterWrapper()

    private val normalCount = 2
    private var needPage = normalCount
    private var sidePage = needPage / normalCount
    private var currentIndex = 0

    //禁止父控件拦截down事件
    private var disallowParentInterceptDownEvent = true

    //手势滑动开始点
    private var startX = 0
    private var startY = 0

    //边界滑动限制
    private var boundarySlipLimit = false

    //无限滚动
    private var infinite = false

    init {
        viewPager2.layoutParams =
            ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(OnPageChangeCallback())
        viewPager2.adapter = viewPager2Adapter
        viewPager2.offscreenPageLimit = 4
        addView(viewPager2)
    }

    private inner class OnPageChangeCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (::changeCallback.isInitialized) {
                changeCallback.onPageScrolled(
                    if (infinite) {
                        toRealPosition(position)
                    } else {
                        position
                    },
                    positionOffset,
                    positionOffsetPixels
                )
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentIndex = position

            if (::changeCallback.isInitialized) {
                changeCallback.onPageSelected(
                    if (infinite) {
                        toRealPosition(position)
                    } else {
                        position
                    }
                )
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            //实现无限轮播
            if (infinite && state == ViewPager2.SCROLL_STATE_DRAGGING) {
                if (currentIndex == sidePage - 1) {
                    //左滑
                    viewPager2.setCurrentItem(
                        viewPager2Adapter.getRealCount() + currentIndex,
                        false
                    )
                } else if (currentIndex == viewPager2Adapter.getRealCount() + sidePage) {
                    //右滑
                    viewPager2.setCurrentItem(sidePage, false)
                }
            }

            if (::changeCallback.isInitialized) {
                changeCallback.onPageScrollStateChanged(state)
            }
        }
    }

    private inner class BannerAdapterWrapper : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private lateinit var adapterWrapper: RecyclerView.Adapter<RecyclerView.ViewHolder>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return adapterWrapper.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            adapterWrapper.onBindViewHolder(holder, toRealPosition(position))
        }

        override fun getItemViewType(position: Int): Int {
            return adapterWrapper.getItemViewType(toRealPosition(position))
        }

        override fun getItemId(position: Int): Long {
            return adapterWrapper.getItemId(toRealPosition(position))
        }

        override fun getItemCount(): Int {
            return if (infinite && getRealCount() > 1) {
                getRealCount() + needPage
            } else {
                getRealCount()
            }
        }

        fun getRealCount(): Int {
            return if (::adapterWrapper.isInitialized) {
                adapterWrapper.itemCount
            } else {
                0
            }
        }

        fun registerAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
            adapterWrapper = adapter
        }
    }

    private fun toRealPosition(position: Int): Int {
        return if (infinite) {
            var realPosition = 0
            if (viewPager2Adapter.getRealCount() > 1) {
                realPosition = (position - sidePage) % viewPager2Adapter.getRealCount()
            }
            if (realPosition < 0) {
                realPosition += viewPager2Adapter.getRealCount()
            }
            realPosition
        } else {
            position
        }
    }

    /**
     * 解决手势冲突
     * 如果设置了userInputEnable=false，那么ViewPager2不应该拦截任何事件；
     * 如果只有一个Item，那么ViewPager2也不应该拦截事件；
     * 如果是多个Item，且当前是第一个页面，那么只能拦截向左的滑动事件，向右的滑动事件就不应该由ViewPager2拦截了；
     * 如果是多个Item，且当前是最后一个页面，那么只能拦截向右的滑动事件，向左的滑动事件不应该由当前的ViewPager2拦截；
     * 如果是多个Item，且是中间页面，那么无论向左还是向右的事件都应该由ViewPager2拦截；
     * ViewPager2是支持竖直滑动的，那么竖直滑动也应该考虑以上条件。
     * @param ev MotionEvent
     * @return Boolean
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        val doNotNeedIntercept = !viewPager2.isUserInputEnabled || viewPager2Adapter.itemCount <= 1
        if (doNotNeedIntercept) {
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(!disallowParentInterceptDownEvent)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - startX)
                val disY = abs(endY - startY)
                if (viewPager2.orientation == ViewPager2.ORIENTATION_VERTICAL) {
                    onVerticalActionMove(endY, disX, disY)
                } else if (viewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    onHorizontalActionMove(endX, disX, disY)
                }
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun onHorizontalActionMove(endX: Int, disX: Int, disY: Int) {
        if (disX > disY) {
            val currentItem = viewPager2.currentItem
            val itemCount = viewPager2Adapter.itemCount
            if (currentItem == 0 && endX - startX > 0) {
                //滑动到最左边
                parent.requestDisallowInterceptTouchEvent(boundarySlipLimit)
            } else {
                //滑动到最右边
                parent.requestDisallowInterceptTouchEvent(
                    (currentItem != itemCount - 1 || boundarySlipLimit)
                            || endX - startX >= 0
                )
            }
        } else if (disY > disX) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun onVerticalActionMove(endY: Int, disX: Int, disY: Int) {
        val currentItem = viewPager2.currentItem
        val itemCount = viewPager2Adapter.itemCount
        if (disY > disX) {
            if (currentItem == 0 && endY - startY > 0) {
                parent.requestDisallowInterceptTouchEvent(boundarySlipLimit)
            } else {
                parent.requestDisallowInterceptTouchEvent(
                    (currentItem != itemCount - 1 || boundarySlipLimit)
                            || endY - startY >= 0
                )
            }
        } else if (disX > disY) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    /**
     * 设置是否允许在ViewPager2Container的{@link MotionEvent#ACTION_DOWN}事件中禁止父View对事件的拦截，该方法
     * 用于解决CoordinatorLayout+CollapsingToolbarLayout在嵌套ViewPager2Container时引起的滑动冲突问题。
     *
     * @param disallowParentInterceptDownEvent
     * 是否允许ViewPager2Container在{@link MotionEvent#ACTION_DOWN}事件中禁止父View拦截事件，默认值为false
     * true:不允许ViewPager2Container在{@link MotionEvent#ACTION_DOWN}时间中禁止父View的时间拦截，
     * 设置disallowIntercept为true可以解决CoordinatorLayout+CollapsingToolbarLayout的滑动冲突
     * false:允许ViewPager2Container在{@link MotionEvent#ACTION_DOWN}时间中禁止父View的时间拦截，
     */
    fun disallowParentInterceptDownEvent(disallowParentInterceptDownEvent: Boolean) {
        this.disallowParentInterceptDownEvent = disallowParentInterceptDownEvent
    }

    /**
     * 设置一屏多页
     *
     * @param tlWidth Int
     * @param brWidth Int
     * @param pageMargin Int
     */
    fun setEffectMultiplePages(tlWidth: Int, brWidth: Int, pageMargin: Int): Banner {
        compositePageTransformer.addTransformer(MarginPageTransformer(pageMargin))
        val recyclerView = viewPager2.getChildAt(0) as RecyclerView
        if (viewPager2.orientation == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(
                viewPager2.paddingLeft,
                tlWidth + abs(pageMargin),
                viewPager2.paddingRight,
                brWidth + abs(pageMargin)
            )
        } else {
            recyclerView.setPadding(
                tlWidth + abs(pageMargin),
                viewPager2.paddingLeft,
                brWidth + abs(pageMargin),
                viewPager2.paddingRight
            )
        }

        recyclerView.clipToPadding = false
        compositePageTransformer.addTransformer(ViewPager2ScaleInTransformer())
        needPage = normalCount + normalCount
        sidePage = normalCount
        return this
    }

    /**
     *
     * 设置banner
     * 需要放在
     * @param adapter Adapter<ViewHolder> 适配器
     * @param infinite Boolean 是否无限滚动，为true时开始无限滚动功能，boundarySlipLimit会被设置为true
     * @param boundarySlipLimit Boolean 是否边界滑动限制，为true时banner滑动到边界时不会触发父控件的手势
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setBanner(
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        infinite: Boolean = false,
        boundarySlipLimit: Boolean = false,
    ) {
        viewPager2Adapter.registerAdapter(adapter)
        if (infinite) {
            this.boundarySlipLimit = true
            this.infinite = true
        } else {
            this.infinite = false
            this.boundarySlipLimit = boundarySlipLimit
        }

        if (sidePage == normalCount) {
            viewPager2.adapter = viewPager2Adapter
        } else {
            viewPager2Adapter.notifyDataSetChanged()
        }
        setCurrentItem(0, false)
    }

    /**
     * 设置banner位置
     *
     * @param position Int 需要指定的位置
     * @param smoothScroll Boolean 滑动动画
     */
    fun setCurrentItem(position: Int, smoothScroll: Boolean) {
        currentIndex = if (infinite) {
            position + sidePage
        } else {
            position
        }
        viewPager2.setCurrentItem(currentIndex, smoothScroll)
    }

    fun setChangeCallback(changeCallback: ViewPager2.OnPageChangeCallback) {
        this.changeCallback = changeCallback
    }
}