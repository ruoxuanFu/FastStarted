package com.fsdk.faststarted.ui.homepage.home.articlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elvishew.xlog.XLog
import com.fsdk.faststarted.http.HomePageData
import com.fsdk.faststarted.utils.coroutineUtil.launchMain

class ArticleListViewModel(private val repository: ArticleListRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ArticleList Fragment"
    }
    val text: LiveData<String> = _text

    private val _articleListData: MutableLiveData<HomePageData> = MutableLiveData()

    val articleListData: LiveData<HomePageData> = _articleListData

    fun getArticleListData(pageIndex: Int) {
        launchMain {
            val data = try {
                repository.getArticleListData(pageIndex)
            } catch (e: Exception) {
                XLog.e(e.message)
                null
            }
            _articleListData.value = data?.data
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ArticleListViewModelFactory(private val repository: ArticleListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleListViewModel(repository) as T
    }
}