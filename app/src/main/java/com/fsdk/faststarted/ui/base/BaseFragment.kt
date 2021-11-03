package com.fsdk.faststarted.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment(), BaseBinding<VDB> {
    lateinit var fBinding: VDB
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fBinding = getViewBinding(inflater, container)
        return fBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fBinding.initBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::fBinding.isInitialized) {
            fBinding.unbind()
        }
    }
}