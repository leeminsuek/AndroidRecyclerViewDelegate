package com.lib.minshoki.recyclerviewdelegate.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lib.minshoki.recyclerviewdelegate.BaseViewModel
import com.lib.minshoki.recyclerviewdelegate.viewholder.BaseViewHolder

abstract class BaseRecyclerViewAdapter(
        var context: Context,
        var baseViewModel: BaseViewModel = BaseViewModel(context)
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder<*>)?.onBind(baseViewModel.getPairFirst(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateView(parent, viewType)
    }

    override fun getItemCount(): Int {
        return baseViewModel.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return baseViewModel.getItemViewType(position)
    }

    fun clear() {
        baseViewModel.clear()
    }

    fun add(item: Any?, viewType: Int) {
        baseViewModel.add(item, viewType)
    }
}