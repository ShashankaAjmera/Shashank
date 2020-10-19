package com.assignement.jet2.network

import com.assignement.jet2.model.ArticleModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    @GET("/blogs")
    fun getArticleList(
        @Query("page") page: Int,
        @Query("limit") apiKey: Int
    ): Observable<Array<ArticleModel.Article>>
}