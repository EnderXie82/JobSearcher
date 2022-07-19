package com.enderx.jobsearcher.core.component.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class GenericRecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {
    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private val items: MutableList<Any> = ArrayList()
    private var itemsDiffCallback: ItemsDiffCallback? = null
    val bindings: HashMap<Int, RecyclerViewViewHolder<ViewBinding, Any>> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(getBinding(viewType).inflate(parent))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getBinding((calculateItemViewType(items[position]::class))).bind(
            holder.binding,
            items[position]
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            getBinding(calculateItemViewType(items[position]::class)).bind(
                holder.binding,
                items[position],
                payloads
            )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = calculateItemViewType(items[position]::class)

    fun setItemDiffCallback(itemsDiffCallback: ItemsDiffCallback) {
        this.itemsDiffCallback = itemsDiffCallback
    }

    fun updateItems(items: List<Any>) {
        itemsDiffCallback?.let {
            val diffCallback = it.apply {
                oldItems = this@GenericRecyclerViewAdapter.items
                newItems = items
            }
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.items.clear()
            this.items.addAll(items)
            diffResult.dispatchUpdatesTo(this)
        } ?: run {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }
    }

    private fun getBinding(viewType: Int): RecyclerViewViewHolder<ViewBinding, Any> {
        return bindings[viewType] ?: run {
            val itemType = items.first {
                calculateItemViewType(it::class) == viewType
            }::class.simpleName
            throw IllegalStateException("No item bindings for item type $itemType found")
        }
    }

    companion object {
        fun calculateItemViewType(clazz: KClass<*>): Int = clazz.hashCode()
    }
}