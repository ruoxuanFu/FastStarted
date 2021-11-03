package com.fsdk.faststarted.ui.homepage.dashboard

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.fsdk.faststarted.databinding.BottomFragmentDashboardBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.ui.homepage.HomePageRepository
import com.fsdk.faststarted.ui.homepage.HomePageVm
import com.fsdk.faststarted.ui.homepage.HomePageVmFactory

class DashboardFragment : BaseFragment<BottomFragmentDashboardBinding>() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private val activityViewModel: HomePageVm by activityViewModels {
        HomePageVmFactory(HomePageRepository())
    }

    override fun BottomFragmentDashboardBinding.initBinding() {
        dashboardViewModel =
            ViewModelProvider(this@DashboardFragment).get(DashboardViewModel::class.java)
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            fBinding.textDashboard.text = it
        }
    }
}