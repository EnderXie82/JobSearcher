package com.enderx.jobsearcher.core.component.recyclerView

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    val recyclerViewAdapter = GenericRecyclerViewAdapter()

    init {
        adapter = recyclerViewAdapter
    }

    inline fun <T : ViewBinding, reified I : Any> addItemBindings(viewHolders: RecyclerViewViewHolder<T, I>) {
        @Suppress("UNCHECKED_CAST")
        recyclerViewAdapter.bindings[GenericRecyclerViewAdapter.calculateItemViewType(I::class)] =
            viewHolders as RecyclerViewViewHolder<ViewBinding, Any>
    }

    fun setItems(items: List<Any>?) = recyclerViewAdapter.updateItems(items ?: emptyList())
}