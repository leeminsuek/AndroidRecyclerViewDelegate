package com.lib.minshoki.recyclerviewdelegate

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

open class RecyclerViewEndlessScroll : RecyclerView.OnScrollListener {

    private var layoutManagerType: LAYOUT_MANAGER_TYPE? = null
    private var lastPositions: IntArray? = null
    private var lastVisibleItemPosition: Int = 0
    private var currentScrollState = 0

    var onLoadMore: OnLoadMore? = null

    enum class LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    interface OnLoadMore {
        fun onLoadMore()
    }

    constructor() {}

    fun setLayoutManagerType(layoutManagerType: LAYOUT_MANAGER_TYPE) {
        this.layoutManagerType = layoutManagerType
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView!!.layoutManager
        if (layoutManagerType == null) {
            layoutManagerType = if (layoutManager is LinearLayoutManager) {
                LAYOUT_MANAGER_TYPE.LINEAR
            } else if (layoutManager is GridLayoutManager) {
                LAYOUT_MANAGER_TYPE.GRID
            } else if (layoutManager is StaggeredGridLayoutManager) {
                LAYOUT_MANAGER_TYPE.STAGGERED_GRID
            } else {
                throw RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }

        when (layoutManagerType) {
            RecyclerViewEndlessScroll.LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
            RecyclerViewEndlessScroll.LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager)
                    .findLastVisibleItemPosition()
            RecyclerViewEndlessScroll.LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        currentScrollState = newState
        val layoutManager = recyclerView!!.layoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        if (visibleItemCount > 0 && lastVisibleItemPosition >= totalItemCount - 1) {
            if (onLoadMore != null) {
                onLoadMore!!.onLoadMore()
            }
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }
}