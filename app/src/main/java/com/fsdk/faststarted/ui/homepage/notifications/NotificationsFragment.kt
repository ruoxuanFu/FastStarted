package com.fsdk.faststarted.ui.homepage.notifications

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
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
}