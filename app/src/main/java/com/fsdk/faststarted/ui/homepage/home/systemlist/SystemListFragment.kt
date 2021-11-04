package com.fsdk.faststarted.ui.homepage.home.systemlist

import androidx.lifecycle.ViewModelProvider
import com.fsdk.faststarted.databinding.FragmentSystemListBinding
import com.fsdk.faststarted.ui.base.BaseFragment

class SystemListFragment : BaseFragment<FragmentSystemListBinding>() {

    private lateinit var vm: SystemListViewModel

    override fun FragmentSystemListBinding.initBinding() {
        vm = ViewModelProvider(this@SystemListFragment).get(SystemListViewModel::class.java)

        vm.text.observe(this@SystemListFragment) {
            fBinding.textSystemList.text = it
        }
    }

}