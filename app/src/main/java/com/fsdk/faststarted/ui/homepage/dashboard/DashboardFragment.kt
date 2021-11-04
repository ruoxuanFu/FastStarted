package com.fsdk.faststarted.ui.homepage.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.elvishew.xlog.XLog
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

    override fun onAttach(context: Context) {
        XLog.d("onAttach ${this::class.java.name}")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        XLog.d("onCreate ${this::class.java.name}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        XLog.d("onCreateView ${this::class.java.name}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        XLog.d("onViewCreated ${this::class.java.name}")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        XLog.d("onStart ${this::class.java.name}")
        super.onStart()
    }

    override fun onResume() {
        XLog.d("onResume ${this::class.java.name}")
        super.onResume()
    }
}