package com.lib.minshoki.recyclerviewdelegate.viewholder

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseViewHolder<in ITEM: Any>(
        @LayoutRes layoutRes: Int,
        parent: ViewGroup?
): RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false)) {
    var context = parent?.context

    fun onBind(item: Any?) {
        onViewBind(item as? ITEM?)
    }

    abstract fun onViewBind(item: ITEM?)
}