package com.fsdk.faststarted.ui.homepage.home.usefullist

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fsdk.faststarted.databinding.FragmentUsefulListBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.utils.AppGlobal
import com.fsdk.faststarted.utils.dp
import kotlin.math.roundToInt

class UsefulListFragment : BaseFragment<FragmentUsefulListBinding>() {

    private lateinit var vm: UsefulListViewModel

    override fun FragmentUsefulListBinding.initBinding() {
        vm = ViewModelProvider(this@UsefulListFragment).get(UsefulListViewModel::class.java)

        initBanner()
    }

    private fun initBanner() {
        fBinding.banner.setEffectMultiplePages(
            20.dp.roundToInt(),
            20.dp.roundToInt(),
            10.dp.roundToInt()
        ).setBanner(
            adapter = AdBannerAdapter(AppGlobal.getApplication()) as RecyclerView.Adapter<RecyclerView.ViewHolder>,
            infinite = true,
            boundarySlipLimit = false
        )
    }

}