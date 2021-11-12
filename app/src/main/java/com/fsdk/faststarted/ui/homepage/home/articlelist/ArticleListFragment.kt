package com.fsdk.faststarted.ui.homepage.home.articlelist

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.fsdk.faststarted.databinding.FragmentArticleListBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.utils.AppGlobal
import com.fsdk.faststarted.widget.banner.ViewPager2ScaleInTransformer
import com.fsdk.faststarted.utils.dp
import kotlin.math.abs
import kotlin.math.roundToInt

class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    private val vm by viewModels<ArticleListViewModel> {
        ArticleListViewModelFactory(ArticleListRepository())
    }

    private var currentBannerIndex = 0

    private lateinit var bannerAdapter: BannerAdapter

    override fun FragmentArticleListBinding.initBinding() {
        initBanner()
    }

    private fun initBanner() {
        bannerAdapter = BannerAdapter(AppGlobal.getApplication())

        fBinding.flBanner.boundarySlipLimit = true
        fBinding.viewPager.apply {
            orientation = ORIENTATION_HORIZONTAL
            adapter = bannerAdapter
            offscreenPageLimit = 1

            val transformer = CompositePageTransformer()

            val tlWidth = 20.dp
            val brWidth = 20.dp
            val pageMargin = 10.dp
            transformer.addTransformer(MarginPageTransformer(pageMargin.roundToInt()))
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.setPadding(
                (tlWidth + abs(pageMargin)).roundToInt(),
                paddingLeft,
                (brWidth + abs(pageMargin)).roundToInt(),
                paddingRight
            )
            recyclerView.clipToPadding = false

            transformer.addTransformer(ViewPager2ScaleInTransformer())
            setPageTransformer(transformer)

            //添加一些互动比如指示器
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //实现无限轮播
                    currentBannerIndex = position
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //实现无限轮播
                    if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                        if (currentBannerIndex == BannerAdapter.sidePage - 1) {
                            //左滑
                            setCurrentItem(
                                bannerAdapter.getRealCount() + currentBannerIndex,
                                false
                            )
                        } else if (currentBannerIndex == bannerAdapter.getRealCount() + BannerAdapter.sidePage) {
                            //右滑
                            setCurrentItem(BannerAdapter.sidePage, false)
                        }
                    }
                }
            })

            setRealCurrentItem(currentBannerIndex, false)
        }
    }

    fun setRealCurrentItem(position: Int, smoothScroll: Boolean) {
        fBinding.viewPager.setCurrentItem(position + BannerAdapter.sidePage, smoothScroll)
    }

    fun getCurrentPager(): Int {
        val position: Int = bannerAdapter.toRealPosition(currentBannerIndex)
        return position.coerceAtLeast(0)
    }

}