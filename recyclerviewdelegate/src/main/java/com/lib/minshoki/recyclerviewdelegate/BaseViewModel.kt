package com.lib.minshoki.recyclerviewdelegate

import android.content.Context
import android.util.Log
import android.view.View
import com.lib.minshoki.recyclerviewdelegate.adapter.BaseRecyclerViewAdapter

open class BaseViewModel(override val context: Context) : BaseAdapterNavigator {

    var adapterEmptyView: View? = null
    var adapterEmptyDefaultCount: Int = 0
    var isLoadMore: Boolean = false
    var endlessScrollListener: (() -> Unit)? = null

    protected val itemList = mutableListOf<Pair<Any, ViewHolderSealedData>>()
    protected var viewTypeData = mutableMapOf<Class<*>, Int>().apply {
        put(BaseRecyclerViewAdapter.Loading::class.java, BaseRecyclerViewAdapter.Type.LOADING.viewType)
    }

    fun loadingProvider(): LoadingProvider {
        return LoadingProvider(R.layout.row_loading)
    }

    fun register(clazz: Class<*>, viewType: Int) {
        if (!viewTypeData.containsKey(clazz)) viewTypeData[clazz] = viewType
    }

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
        return when (item) {
            is ViewHolderData -> item.viewType
            else -> -1
        }
    }

    override fun add(item: Any) {
        viewTypeData[item::class.java]?.let {
            itemList.add(Pair(item, ViewHolderData(it)))
        } ?: Log.w(this::class.java.simpleName, "not find a matches view type to class ####${item::class.java.simpleName}#### you can call register(clazz, viewType) function in YourAdapter")

    }

    override fun clear() {
        itemList.clear()
    }

    override fun removeAtPosition(position: Int) {
        itemList.removeAt(position)
    }

    override fun removeAtViewType(viewType: Int) {
        var result: MutableList<Pair<Any?, ViewHolderSealedData>> = itemList.filter { (it.second as ViewHolderData).viewType == viewType }.toMutableList()
        itemList.removeAll(result)
    }

    override fun getItemCount(): Int = itemList.size

    open class Builder(var context: Context) {

        private var emptyView: View? = null
        private var defaultCount: Int = 0
        private var loadMore: Boolean = false
        private var endlessScroll: (() -> Unit)? = null

        fun emptyView(view: View?, defaultCount: Int = 0): Builder  {
            this.emptyView = view
            this.defaultCount = defaultCount
            return this
        }

        fun loadMore(loadMore: Boolean = false): Builder {
            this.loadMore = loadMore
            return this
        }

        fun endlessScroll(listener: (() -> Unit)?): Builder {
            loadMore(true)
            this.endlessScroll = listener
            return this
        }

        fun build(): BaseViewModel {
            return BaseViewModel(context).apply {
                adapterEmptyView = emptyView
                adapterEmptyDefaultCount = defaultCount
                isLoadMore = loadMore
                endlessScrollListener = endlessScroll
            }
        }
    }
}
