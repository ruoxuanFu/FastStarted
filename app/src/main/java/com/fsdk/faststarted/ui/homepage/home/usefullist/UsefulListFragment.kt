package com.fsdk.faststarted.ui.homepage.home.usefullist

import androidx.lifecycle.ViewModelProvider
import com.fsdk.faststarted.databinding.FragmentUsefulListBinding
import com.fsdk.faststarted.ui.base.BaseFragment

class UsefulListFragment : BaseFragment<FragmentUsefulListBinding>() {

    private lateinit var vm: UsefulListViewModel

    override fun FragmentUsefulListBinding.initBinding() {
        vm = ViewModelProvider(this@UsefulListFragment).get(UsefulListViewModel::class.java)

        vm.text.observe(this@UsefulListFragment) {
            fBinding.textUsefulList.text = it
        }
    }

}