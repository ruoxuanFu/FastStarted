package com.fsdk.faststarted.ui.homepage.person

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.databinding.BottomFragmentPersonBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.ui.homepage.HomePageRepository
import com.fsdk.faststarted.ui.homepage.HomePageVm
import com.fsdk.faststarted.ui.homepage.HomePageVmFactory

class PersonFragment : BaseFragment<BottomFragmentPersonBinding>() {

    private lateinit var personViewModel: PersonViewModel

    private val activityViewModel: HomePageVm by activityViewModels {
        HomePageVmFactory(HomePageRepository())
    }

    override fun BottomFragmentPersonBinding.initBinding() {
        personViewModel =
            ViewModelProvider(this@PersonFragment).get(PersonViewModel::class.java)
        personViewModel.text.observe(viewLifecycleOwner) {
            fBinding.textPerson.text = it
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