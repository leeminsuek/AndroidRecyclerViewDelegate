package com.lib.minshoki.recyclerviewdelegate

sealed class ViewHolderSealedData

data class ViewHolderData(val viewType: Int): ViewHolderSealedData()