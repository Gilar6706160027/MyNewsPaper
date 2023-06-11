package com.gilar.mynewspaper.util.swipeDetector

import androidx.recyclerview.widget.RecyclerView
import com.gilar.mynewspaper.R

interface RecyclerViewSwipe {
    fun onSwipeLeft(viewHolder: RecyclerView.ViewHolder)
    fun  addSwipeLeftBackgroundColor():Int
    fun addSwipeLeftActionIcon():Int

}