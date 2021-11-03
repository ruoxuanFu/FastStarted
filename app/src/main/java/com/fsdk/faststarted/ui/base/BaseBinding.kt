package com.fsdk.faststarted.ui.base

import androidx.databinding.ViewDataBinding

interface BaseBinding<VDB : ViewDataBinding> {

    fun VDB.initBinding()
}