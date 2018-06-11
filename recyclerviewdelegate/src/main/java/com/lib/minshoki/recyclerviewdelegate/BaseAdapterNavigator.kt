package com.lib.minshoki.recyclerviewdelegate

import android.content.Context

interface BaseAdapterNavigator {
    val context: Context

    fun getItemViewType(position: Int): Int
    fun add(item: Any?, viewType: Int)
    fun clear()
    fun getItemCount(): Int
}