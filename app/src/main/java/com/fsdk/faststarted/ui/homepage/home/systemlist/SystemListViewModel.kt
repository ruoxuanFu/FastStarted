package com.fsdk.faststarted.ui.homepage.home.systemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SystemListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is SystemList Fragment"
    }
    val text: LiveData<String> = _text
}