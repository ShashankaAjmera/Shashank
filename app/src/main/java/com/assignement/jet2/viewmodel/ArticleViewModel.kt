package com.assignement.jet2.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.assignement.jet2.model.ArticleModel
import com.assignement.jet2.network.ArticleApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleViewModel : BaseViewModel() {
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Unit> = MutableLiveData()
    val noMoreDataToLoad: MutableLiveData<Unit> = MutableLiveData()
    val updateList: MutableLiveData<List<ArticleModel.Article>> = MutableLiveData()
    var page: Int = 1
    var articleList : ArrayList<ArticleModel.Article> = ArrayList()

    @Inject
    lateinit var articleApi: ArticleApi

    private lateinit var subscription: Disposable

    init {
        loadArticles(page++)
    }

    fun loadArticles(page: Int) {
        subscription = articleApi.getArticleList(page, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingVisibility.value = View.VISIBLE }
            .doOnTerminate { loadingVisibility.value = View.GONE }
            .subscribe(
                { result -> onRetrieveArticleListSuccess(result) },
                { errorMessage.postValue(Unit) }
            )
    }

    private fun onRetrieveArticleListSuccess(articleList: Array<ArticleModel.Article>) {
        if(articleList.toList().isNotEmpty()) {
            this.articleList.addAll(articleList.toList())
            updateList.postValue(articleList.toMutableList())
        } else {
            noMoreDataToLoad.postValue(Unit)
        }
    }
}