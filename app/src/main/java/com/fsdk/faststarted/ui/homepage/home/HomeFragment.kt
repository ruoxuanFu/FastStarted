package com.fsdk.faststarted.ui.homepage.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.fsdk.faststarted.databinding.BottomFragmentHomeBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.ui.homepage.HomePageRepository
import com.fsdk.faststarted.ui.homepage.HomePageVm
import com.fsdk.faststarted.ui.homepage.HomePageVmFactory

class HomeFragment : BaseFragment<BottomFragmentHomeBinding>() {

    private lateinit var homeViewModel: HomeViewModel

    private val activityViewModel: HomePageVm by activityViewModels {
        HomePageVmFactory(HomePageRepository())
    }

    override fun BottomFragmentHomeBinding.initBinding() {
        homeViewModel =
            ViewModelProvider(this@HomeFragment).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner) {
            fBinding.textHome.text = it
        }
    }
}