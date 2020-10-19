package com.assignement.jet2.model

object ArticleModel {

    data class Article(
        val id: String,
        val createdAt: String,
        val content: String,
        val readTime: String,
        val comments: Int,
        val likes: Int,
        val media: List<Media>,
        val user: List<User>
    )

    data class Media(
        val id: String,
        val blogId: String,
        val createdAt: String,
        val image: String,
        val title: String,
        val url: String
    )


    data class User(
        val id: String,
        val blogId: String,
        val createdAt: String,
        val name: String,
        val avatar: String,
        val lastname: String,
        val city: String,
        val designation: String,
        val about: String
    )
}