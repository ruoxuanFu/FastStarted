package com.fsdk.faststarted.ui.homepage.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.databinding.BottomFragmentNotificationsBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.ui.homepage.HomePageRepository
import com.fsdk.faststarted.ui.homepage.HomePageVm
import com.fsdk.faststarted.ui.homepage.HomePageVmFactory

class NotificationsFragment : BaseFragment<BottomFragmentNotificationsBinding>() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private val activityViewModel: HomePageVm by activityViewModels {
        HomePageVmFactory(HomePageRepository())
    }

    override fun BottomFragmentNotificationsBinding.initBinding() {
        notificationsViewModel =
            ViewModelProvider(this@NotificationsFragment).get(NotificationsViewModel::class.java)
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            fBinding.textNotifications.text = it
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        XLog.d("onAttach ${this::class.java.name}")
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