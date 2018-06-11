package com.minshoki.lib

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lib.minshoki.recyclerviewdelegate.BaseViewModel
import com.lib.minshoki.recyclerviewdelegate.adapter.BaseRecyclerViewAdapter
import com.lib.minshoki.recyclerviewdelegate.viewholder.BaseViewHolder

class TestAdapter(context: Context) : BaseRecyclerViewAdapter(context) {
    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            1 -> VH1(parent)
            2 -> VH2(parent)
            else -> null!!
        }
    }

    open class VH1(parent: ViewGroup): BaseViewHolder<TestVO>(R.layout.row_1, parent) {
        override fun onViewBind(item: TestVO?) {
            with(itemView) {

            }
        }
    }

    open class VH2(parent: ViewGroup): BaseViewHolder<TestVO>(R.layout.row_2, parent) {
        override fun onViewBind(item: TestVO?) {
            with(itemView) {

            }
        }
    }
}