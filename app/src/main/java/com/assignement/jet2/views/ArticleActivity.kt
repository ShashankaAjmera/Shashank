package com.assignement.jet2.views

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignement.jet2.R
import com.assignement.jet2.adapters.ArticleAdapter
import com.assignement.jet2.model.ArticleModel
import com.assignement.jet2.viewmodel.ArticleViewModel


class ArticleActivity : AppCompatActivity() {
    private var adapter: ArticleAdapter? = null
    private var progressBar: ProgressBar? = null
    private lateinit var viewModel: ArticleViewModel
    private var isReached = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.loadingVisibility.observe(this, Observer {
            progressDialogVisibility(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            if (it != null) showError()
        })
        viewModel.updateList.observe(this, Observer {
            updateArticleList(it)
        })
        viewModel.noMoreDataToLoad.observe(this, Observer {
            showNoData()
        })

        setContentView(R.layout.activity_main)
        initializeViews()
    }

    private fun progressDialogVisibility(isVisible: Int) {
        progressBar?.visibility = isVisible
        isLoading = isVisible == View.VISIBLE
    }

    private fun showError() {
        Toast.makeText(this, getString(R.string.post_error), Toast.LENGTH_LONG).show()
    }

    private fun initializeViews() {
        progressBar = findViewById(R.id.pb_loader)
        adapter = ArticleAdapter(viewModel.articleList)
        val articleRecyclerView = findViewById<RecyclerView>(R.id.rv_article)
        articleRecyclerView.itemAnimator = DefaultItemAnimator()
        articleRecyclerView.layoutManager = LinearLayoutManager(this)
        articleRecyclerView.adapter = adapter

        articleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = linearLayoutManager!!.findLastVisibleItemPosition()
                if (!isReached && !isLoading && lastVisibleItemPosition >= adapter!!.itemCount - 1) {
                    viewModel.loadArticles(viewModel.page++)
                }
            }
        })

    }

    private fun updateArticleList(articleList: List<ArticleModel.Article>?) {
        if (articleList != null)
            adapter?.updateArticleList(articleList)
    }


    private fun showNoData() {
        isReached = true
        Toast.makeText(this, R.string.no_more_data, Toast.LENGTH_SHORT).show()
    }
}

