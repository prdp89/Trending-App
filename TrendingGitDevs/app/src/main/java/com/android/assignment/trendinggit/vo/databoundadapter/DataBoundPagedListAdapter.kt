package com.android.assignment.trendinggit.vo.databoundadapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.cogni.newsapp.vo.databoundadapter.DataBoundViewHolder

abstract class DataBoundPagedListAdapter<T, V : ViewDataBinding>(
    mAppExecutors: AppExecutors,
    mDiffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(mDiffCallback)
        .setBackgroundThreadExecutor(mAppExecutors.diskIO())
        .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position)!!, position)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    protected abstract fun bind(binding: V, item: T, position: Int)
}
