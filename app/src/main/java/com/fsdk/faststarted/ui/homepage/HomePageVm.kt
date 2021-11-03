package com.fsdk.faststarted.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.http.HomePageData
import com.fsdk.faststarted.utils.coroutineUtil.launchMain

class HomePageVm(private val repository: HomePageRepository) : ViewModel() {

    private val _homePageData: MutableLiveData<HomePageData> = MutableLiveData()

    val homePageData: LiveData<HomePageData> = _homePageData

    fun getHomePageData(pageIndex: Int) {
        launchMain {
            val data = try {
                repository.getHomePageData(pageIndex)
            } catch (e: Exception) {
                XLog.e(e.message)
                null
            }
            _homePageData.value = data?.data
        }
    }
}

class HomePageVmFactory(private val repository: HomePageRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomePageVm(repository) as T
    }

}