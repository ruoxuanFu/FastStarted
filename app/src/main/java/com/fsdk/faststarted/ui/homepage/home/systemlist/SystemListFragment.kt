package com.fsdk.faststarted.ui.homepage.home.systemlist

import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.databinding.FragmentSystemListBinding
import com.fsdk.faststarted.ui.base.BaseFragment
import com.fsdk.faststarted.utils.AppGlobal
import com.fsdk.faststarted.utils.coroutineUtil.launchMain
import kotlinx.coroutines.flow.collect

class SystemListFragment : BaseFragment<FragmentSystemListBinding>() {

    private lateinit var vm: SystemListViewModel

    private var refreshing = false
    private lateinit var listAdapter: SystemListPagingAdapter

    override fun FragmentSystemListBinding.initBinding() {
        vm = ViewModelProvider(this@SystemListFragment).get(SystemListViewModel::class.java)

        initList()
    }

    private fun initList() {
        fBinding.refreshLayout.apply {
            setEnableAutoLoadMore(false)

            setOnRefreshListener {
                XLog.d("setOnRefreshListener")
                refreshing = true
                listAdapter.refresh()
            }

            setOnLoadMoreListener {
                XLog.d("setOnLoadMoreListener")
            }
        }

        listAdapter = SystemListPagingAdapter()

        fBinding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(AppGlobal.getApplication(), LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        launchMain {
            vm.listDataFlow.collect {
                listAdapter.submitData(it)
            }
        }

        listAdapter.addLoadStateListener {
            XLog.d(it.source)
            when (it.append) {
                is LoadState.Loading -> {
                    if (!refreshing) {
                        fBinding.refreshLayout.autoLoadMore()
                    }
                }
                is LoadState.NotLoading -> {
                    if (refreshing) {
                        fBinding.refreshLayout.finishRefresh()
                        refreshing = false
                    } else {
                        fBinding.refreshLayout.finishLoadMore()
                    }
                }
                else -> {
                    XLog.d(it.append)
                }
            }

            when (it.refresh) {
                is LoadState.Error -> {
                    refreshing = false
                    listAdapter.refresh()
                }

                else -> {
                    XLog.d(it.refresh)
                }
            }

        }
    }

}