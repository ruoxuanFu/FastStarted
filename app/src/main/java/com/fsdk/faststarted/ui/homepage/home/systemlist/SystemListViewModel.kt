package com.fsdk.faststarted.ui.homepage.home.systemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fsdk.faststarted.http.HttpUtil

class SystemListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is SystemList Fragment"
    }
    val text: LiveData<String> = _text

    val listDataFlow = Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 1)) {
        SystemListPagingSource(HttpUtil.service)
    }.flow.cachedIn(viewModelScope)
}