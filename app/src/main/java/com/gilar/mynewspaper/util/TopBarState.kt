package com.gilar.mynewspaper.util

import com.gilar.mynewspaper.model.Article

sealed class TopBarState(
    val selectedItem:MutableList<Article>?=null
) {
    class NormalState() : TopBarState()
    class SearchState(val query: String? = null) : TopBarState()
    class CategoryState(val category: String? = null) : TopBarState()
    class SelectionState(private var selectedItemList: MutableList<Article>) : TopBarState(selectedItemList)

}