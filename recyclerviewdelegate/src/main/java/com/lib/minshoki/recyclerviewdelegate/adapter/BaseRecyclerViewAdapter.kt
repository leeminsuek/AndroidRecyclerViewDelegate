package com.lib.minshoki.recyclerviewdelegate.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lib.minshoki.recyclerviewdelegate.BaseViewModel
import com.lib.minshoki.recyclerviewdelegate.RecyclerViewEndlessScroll
import com.lib.minshoki.recyclerviewdelegate.viewholder.BaseViewHolder
import com.lib.minshoki.recyclerviewdelegate.viewholder.LoadingViewHolder

abstract class BaseRecyclerViewAdapter(
        var context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkEmpty()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            checkEmpty()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            checkEmpty()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            checkEmpty()
        }

        fun checkEmpty() {
            baseViewModel.adapterEmptyView?.let { if (itemCount == baseViewModel.adapterEmptyDefaultCount) it.visibility = View.VISIBLE else it.visibility = View.GONE }
        }
    }



    abstract fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun registerViewType()

    protected var parentRecyclerView: RecyclerView? = null
    protected var baseViewModel: BaseViewModel = BaseViewModel(context)
    protected var onLoadMoreListener: (() -> Unit)? = null
    protected var endlessScroll: RecyclerViewEndlessScroll = RecyclerViewEndlessScroll().apply {
        onLoadMore = object: RecyclerViewEndlessScroll.OnLoadMore {
            override fun onLoadMore() {
                onLoadMoreListener?.let { it.invoke() }
            }
        }
    }
    init {
        registerViewType()

        registerAdapterDataObserver(dataObserver)
    }

    inline fun setting(noinline init: BaseViewModel.Builder.() -> BaseViewModel.Builder) {
        var model = BaseViewModel.Builder(context)
                .init()
                .build()
        baseViewModel.adapterEmptyView = model.adapterEmptyView
        baseViewModel.adapterEmptyDefaultCount = model.adapterEmptyDefaultCount
        baseViewModel.isLoadMore = model.isLoadMore

        parentRecyclerView?.let { rv ->
            if(model.isLoadMore) {
                onLoadMoreListener = model.endlessScrollListener
                rv.addOnScrollListener(endlessScroll)
            } else {
                onLoadMoreListener = null
                rv.removeOnScrollListener(endlessScroll)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        parentRecyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder<*>)?.onBind(baseViewModel.getPairFirst(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Type.LOADING.viewType -> LoadingViewHolder(baseViewModel.loadingProvider().loadingLayoutId, parent)
            else -> onCreateView(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return baseViewModel.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return baseViewModel.getItemViewType(position)
    }

    protected fun register(clazz: Class<*>, viewType: Int) {
        baseViewModel.register(clazz, viewType)
    }

    fun clear() {
        baseViewModel.clear()
    }

    fun add(item: Any) {
        baseViewModel.add(item)
    }

    fun startLoadMore() {
        baseViewModel.add(Loading())
        notifyItemInserted(baseViewModel.getItemCount() - 1)
    }

    fun endLoading() {
        baseViewModel.removeAtPosition(baseViewModel.getItemCount() - 1)
        notifyItemRemoved(baseViewModel.getItemCount())
    }

    fun endLoadMore(item: Any) {
        checkLoading()
        add(item)
        notifyItemInserted(baseViewModel.getItemCount())
    }

    fun endLoadMore(items: MutableList<Any>) {
        checkLoading()
        items.map {
            add(it)
            notifyItemInserted(baseViewModel.getItemCount())
        }
    }

    private fun checkLoading() {
        if (baseViewModel?.getItemCount() > 0) {
            if (getItemViewType(itemCount - 1) == Type.LOADING.viewType) {
                endLoading()
            }
        }
    }

    class Loading

    enum class Type(var viewType: Int) {
        LOADING(9999)
    }
}