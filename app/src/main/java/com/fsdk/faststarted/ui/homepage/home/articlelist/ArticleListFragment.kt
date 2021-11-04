package com.fsdk.faststarted.ui.homepage.home.articlelist

import androidx.fragment.app.viewModels
import com.fsdk.faststarted.databinding.FragmentArticleListBinding
import com.fsdk.faststarted.ui.base.BaseFragment

class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    private val vm by viewModels<ArticleListViewModel> {
        ArticleListViewModelFactory(ArticleListRepository())
    }

    override fun FragmentArticleListBinding.initBinding() {

    }

}