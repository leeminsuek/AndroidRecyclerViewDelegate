package com.lib.minshoki.recyclerviewdelegate

import android.content.Context

open class BaseViewModel(override val context: Context): BaseAdapterNavigator {

    protected val itemList = mutableListOf<Pair<Any?, ViewHolderSealedData>>()

    fun getPair(position: Int): Pair<Any?, ViewHolderSealedData> {
        return itemList[position]
    }

    fun getPairSecond(position: Int): ViewHolderData {
        return getPair(position).second as ViewHolderData
    }

    fun getPairFirst(position: Int): Any? {
        return getPair(position).first
    }

    override fun getItemViewType(position: Int): Int {
        val item = getPairSecond(position)
        return when(item) {
            is ViewHolderData -> item.viewType
            else -> -1
        }
    }

    override fun add(item: Any?, viewType: Int) {
        itemList.add(Pair(item, ViewHolderData(viewType)))
    }

    override fun clear() {
        itemList.clear()
    }

    override fun getItemCount(): Int = itemList.size
}
