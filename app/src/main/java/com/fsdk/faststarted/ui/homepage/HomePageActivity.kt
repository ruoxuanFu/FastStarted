package com.fsdk.faststarted.ui.homepage

import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.R
import com.fsdk.faststarted.databinding.ActivityHomePageBinding
import com.fsdk.faststarted.databinding.LayoutHomePageTabBinding
import com.fsdk.faststarted.ui.base.BaseActivity
import com.fsdk.faststarted.ui.homepage.dashboard.DashboardFragment
import com.fsdk.faststarted.ui.homepage.home.HomeFragment
import com.fsdk.faststarted.ui.homepage.notifications.NotificationsFragment
import com.fsdk.faststarted.ui.homepage.person.PersonFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageActivity : BaseActivity<ActivityHomePageBinding>() {

    private val tabImage = arrayOf(
        R.drawable.tab_home_selector,
        R.drawable.tab_dashboard_selector,
        R.drawable.tab_person_selector,
        R.drawable.tab_notifications_selector
    )

    private val tabText = arrayOf(
        R.string.tab_home,
        R.string.tab_dashboard,
        R.string.tab_person,
        R.string.tab_notifications
    )

    private val tabFragment = arrayOf(
        HomeFragment(),
        DashboardFragment(),
        PersonFragment(),
        NotificationsFragment()
    )

    private val vm by viewModels<HomePageVm> {
        HomePageVmFactory(HomePageRepository())
    }

    override fun ActivityHomePageBinding.initBinding() {
        initTabs()

        vm.currentPageIndex.observe(this@HomePageActivity) {
            XLog.d("当前Tab页面index====>$it")
        }
    }

    private fun initTabs() {

        val viewPagerAdapter =
            HomePageFragmentAdapter(supportFragmentManager, lifecycle, tabFragment.toList())
        binding.vp2Container.adapter = viewPagerAdapter

        //offScreenPageLimit为默认值为-1
        //offScreenPageLimit为-1的时候，使用RecyclerView的缓存机制，也就是懒加载，只加载当前可见的fragment
        //offScreenPageLimit大于1时，才会去实现预加载，被预加载的fragment会走以下声明周期方法
        //onAttach >> onCreate >> onCreateView >> onViewCreated >> onStart
        //官方建议三-四个fragment时开启预加载功能
        binding.vp2Container.offscreenPageLimit = 3

        //禁止滑动
        binding.vp2Container.isUserInputEnabled = false

        binding.vp2Container.registerOnPageChangeCallback(object :
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
                vm.setCurrentPageIndex(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding.tlBottomTab.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tabBinding = tab?.tag as LayoutHomePageTabBinding
                    tabBinding.tvTabContent.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.color_ff0099ff,
                            theme
                        )
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val tabBinding = tab?.tag as LayoutHomePageTabBinding
                    tabBinding.tvTabContent.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.color_ff333333,
                            theme
                        )
                    )
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }

        TabLayoutMediator(binding.tlBottomTab, binding.vp2Container) { tab, position ->
            val binding = createTabView(tabImage[position], tabText[position])
            tab.customView = binding.root
            tab.id = position
            tab.tag = binding
        }.attach()
    }

    private fun createTabView(drawable: Int, string: Int): LayoutHomePageTabBinding {
        val tabBinding = LayoutHomePageTabBinding.inflate(layoutInflater)
        tabBinding.imgTabContent.setImageResource(drawable)
        tabBinding.tvTabContent.text = resources.getString(string)
        return tabBinding
    }
}