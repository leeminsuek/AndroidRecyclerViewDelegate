package com.lib.minshoki.recyclerviewdelegate

import android.support.annotation.LayoutRes
import android.view.View

class LoadingProvider {
    @LayoutRes var loadingLayoutId: Int = 0
    constructor(@LayoutRes layoutId: Int) {
        this.loadingLayoutId = layoutId
    }

}