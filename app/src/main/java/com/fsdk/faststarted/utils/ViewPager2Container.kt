package com.fsdk.faststarted.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

//优化ViewPager2嵌套滑动冲突
//https://juejin.cn/post/6911456860533063688
class ViewPager2Container(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var mViewPager2: ViewPager2? = null

    //禁止父控件拦截down事件
    private var disallowParentInterceptDownEvent = true

    private var startX = 0
    private var startY = 0

    //边界滑动限制
    var boundarySlipLimit = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView is ViewPager2) {
                mViewPager2 = childView
                break
            }
        }
        if (mViewPager2 == null) {
            throw IllegalStateException("ViewPager2Container的子控件中必须包含一个ViewPager2")
        }

    }

    /**
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

        return mViewPager2?.run<ViewPager2, Boolean> {
            val doNotNeedIntercept =
                !isUserInputEnabled || (adapter != null && adapter!!.itemCount <= 1)
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
                    if (orientation == ViewPager2.ORIENTATION_VERTICAL) {
                        onVerticalActionMove(this, endY, disX, disY)
                    } else if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                        onHorizontalActionMove(this, endX, disX, disY)
                    }
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            return super.onInterceptTouchEvent(ev)
        } ?: super.onInterceptTouchEvent(ev)
    }

    private fun onHorizontalActionMove(viewPager2: ViewPager2, endX: Int, disX: Int, disY: Int) {
        viewPager2.adapter?.let {
            if (disX > disY) {
                val currentItem = viewPager2.currentItem
                val itemCount = it.itemCount
                if (currentItem == 0 && endX - startX > 0) {
                    parent.requestDisallowInterceptTouchEvent(boundarySlipLimit)
                } else {
                    parent.requestDisallowInterceptTouchEvent(
                        (currentItem != itemCount - 1 || boundarySlipLimit)
                                || endX - startX >= 0
                    )
                }
            } else if (disY > disX) {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        } ?: return
    }

    private fun onVerticalActionMove(viewPager2: ViewPager2, endY: Int, disX: Int, disY: Int) {
        viewPager2.adapter?.let {
            val currentItem = viewPager2.currentItem
            val itemCount = it.itemCount
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
        } ?: return
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
}