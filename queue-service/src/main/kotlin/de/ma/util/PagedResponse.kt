package de.ma.util

data class PagedResponse<T>(
    var content: List<T> = emptyList(),
    var page: Int = 0,
    var pageCount: Int = 0
)
