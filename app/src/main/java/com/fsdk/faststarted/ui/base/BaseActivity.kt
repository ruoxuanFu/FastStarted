package com.fsdk.faststarted.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.fsdk.faststarted.R
import com.gyf.immersionbar.ktx.fitsStatusBarView
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.showStatusBar
import com.gyf.immersionbar.ktx.statusBarHeight

abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity(), BaseBinding<VDB> {
    val binding: VDB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSystemBar()
        binding.initBinding()
    }

    /**
     * 状态栏导航栏初始化
     */
    open fun initSystemBar() {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            navigationBarColor(R.color.color_ffffffff)
            navigationBarDarkIcon(true)
        }
    }

}