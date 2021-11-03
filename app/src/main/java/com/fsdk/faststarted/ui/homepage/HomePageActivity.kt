package com.fsdk.faststarted.ui.homepage

import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.fsdk.faststarted.R
import com.fsdk.faststarted.databinding.ActivityHomePageBinding
import com.fsdk.faststarted.databinding.LayoutHomePageTabBinding
import com.fsdk.faststarted.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout

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

    private val vm by viewModels<HomePageVm> {
        HomePageVmFactory(HomePageRepository())
    }

    override fun ActivityHomePageBinding.initBinding() {
        initTabs()
    }

    private fun initTabs() {
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

            for (i in tabImage.indices) {
                createTab(
                    id = i,
                    drawable = tabImage[i],
                    string = tabText[i]
                )
            }
        }
    }

    private fun TabLayout.createTab(drawable: Int, string: Int, id: Int) {
        val tabBinding = createTabView(drawable, string)
        addTab(newTab().setCustomView(tabBinding.root).setId(id).setTag(tabBinding))
    }

    private fun createTabView(drawable: Int, string: Int): LayoutHomePageTabBinding {
        val tabBinding = LayoutHomePageTabBinding.inflate(layoutInflater)
        tabBinding.imgTabContent.setImageResource(drawable)
        tabBinding.tvTabContent.text = resources.getString(string)
        return tabBinding
    }
}