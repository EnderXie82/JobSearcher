package com.enderx.jobsearcher.core.component.recyclerView

import androidx.recyclerview.widget.DiffUtil

open class ItemsDiffCallback : DiffUtil.Callback() {

    var oldItems: List<Any> = emptyList()
    var newItems: List<Any> = emptyList()

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] === newItems[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] === newItems[newItemPosition]
}