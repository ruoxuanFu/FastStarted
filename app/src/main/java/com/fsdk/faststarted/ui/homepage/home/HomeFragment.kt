package com.fsdk.faststarted.ui.homepage.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.R
import com.fsdk.faststarted.databinding.BottomFragmentHomeBinding
import com.fsdk.faststarted.databinding.LayoutHomeFragmentTabBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.ui.homepage.HomePageFragmentAdapter
import com.fsdk.faststarted.ui.homepage.HomePageRepository
import com.fsdk.faststarted.ui.homepage.HomePageVm
import com.fsdk.faststarted.ui.homepage.HomePageVmFactory
import com.fsdk.faststarted.ui.homepage.home.articlelist.ArticleListFragment
import com.fsdk.faststarted.ui.homepage.home.systemlist.SystemListFragment
import com.fsdk.faststarted.ui.homepage.home.usefullist.UsefulListFragment
import com.fsdk.faststarted.utils.AppGlobal
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<BottomFragmentHomeBinding>() {

    private lateinit var homeViewModel: HomeViewModel

    //获取activity的vm
    private val activityViewModel: HomePageVm by activityViewModels {
        HomePageVmFactory(HomePageRepository())
    }

    private val tabText = arrayOf(
        R.string.tab_article_list,
        R.string.tab_system_list,
        R.string.tab_useful_list
    )

    private val tabFragment = arrayOf(
        ArticleListFragment(),
        SystemListFragment(),
        UsefulListFragment()
    )

    override fun BottomFragmentHomeBinding.initBinding() {
        homeViewModel =
            ViewModelProvider(this@HomeFragment).get(HomeViewModel::class.java)

        initTabs()
    }

    private fun initTabs() {

        val viewPagerAdapter =
            HomePageFragmentAdapter(parentFragmentManager, lifecycle, tabFragment.toList())
        fBinding.vp2Container.adapter = viewPagerAdapter

        //offScreenPageLimit为默认值为-1
        //offScreenPageLimit为-1的时候，使用RecyclerView的缓存机制，也就是懒加载，只加载当前可见的fragment
        //offScreenPageLimit大于1时，才会去实现预加载，被预加载的fragment会走以下声明周期方法
        //onAttach >> onCreate >> onCreateView >> onViewCreated >> onStart
        //官方建议三-四个fragment时开启预加载功能
        fBinding.vp2Container.offscreenPageLimit = 3

        fBinding.vp2Container.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        fBinding.tabLayoutTop.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tabBinding = tab?.tag as LayoutHomeFragmentTabBinding
                    tabBinding.tvTabContent.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.color_ff0099ff,
                            AppGlobal.getApplication().theme
                        )
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val tabBinding = tab?.tag as LayoutHomeFragmentTabBinding
                    tabBinding.tvTabContent.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.color_ff333333,
                            AppGlobal.getApplication().theme
                        )
                    )
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }

        TabLayoutMediator(fBinding.tabLayoutTop, fBinding.vp2Container) { tab, position ->
            val binding = createTabView(tabText[position])
            tab.customView = binding.root
            tab.id = position
            tab.tag = binding
        }.attach()
    }

    private fun createTabView(string: Int): LayoutHomeFragmentTabBinding {
        val tabBinding = LayoutHomeFragmentTabBinding.inflate(layoutInflater)
        tabBinding.tvTabContent.text = resources.getString(string)
        return tabBinding
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        XLog.d("onAttach ${this::class.java.name}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        XLog.d("onCreate ${this::class.java.name}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        XLog.d("onCreateView ${this::class.java.name}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        XLog.d("onViewCreated ${this::class.java.name}")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        XLog.d("onStart ${this::class.java.name}")
        super.onStart()
    }

    override fun onResume() {
        XLog.d("onResume ${this::class.java.name}")
        super.onResume()
    }
}