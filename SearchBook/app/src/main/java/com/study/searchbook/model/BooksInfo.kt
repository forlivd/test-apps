package com.study.searchbook.model

data class BooksInfo(
    val lastBuildDate: String = "",
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<Book>
)

data class Book(
    val title: String = "",
    val link: String = "",
    val image: String = "",
    val author: String = "",
    val discount: String = "",
    val publisher: String = "",
    val pubdate: String = "",
    val isbn: String = "",
    val description: String = ""
)
