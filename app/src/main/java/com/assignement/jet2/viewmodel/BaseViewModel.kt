package com.assignement.jet2.viewmodel

import androidx.lifecycle.ViewModel
import com.assignement.jet2.network.DaggerViewModelInjector
import com.assignement.jet2.network.NetworkModule
import com.assignement.jet2.network.ViewModelInjector
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    private lateinit var subscription: Disposable
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ArticleViewModel -> injector.inject(this)
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}