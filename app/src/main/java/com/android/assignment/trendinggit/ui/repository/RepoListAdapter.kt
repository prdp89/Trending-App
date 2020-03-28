package com.android.assignment.trendinggit.ui.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.ItemTrendingRepoBinding
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.vo.databoundadapter.DataBoundListAdapter

class RepoListAdapter(
    appExecutors: AppExecutors,
    private val callback: ((TrendingRepoEntity, ItemTrendingRepoBinding) -> Unit)?
) :
    DataBoundListAdapter<TrendingRepoEntity,
            ItemTrendingRepoBinding>(mAppExecutors = appExecutors,
        mDiffCallback = object : DiffUtil.ItemCallback<TrendingRepoEntity>() {
            override fun areItemsTheSame(
                oldItem: TrendingRepoEntity,
                newItem: TrendingRepoEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrendingRepoEntity,
                newItem: TrendingRepoEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTrendingRepoBinding {

        val binding = DataBindingUtil.inflate<ItemTrendingRepoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_repo,
            parent,
            false
        )

        binding.root.setOnClickListener {
            callback?.invoke(binding.entity!!, binding)
        }

        return binding
    }

    override fun bind(
        binding: ItemTrendingRepoBinding,
        item: TrendingRepoEntity,
        position: Int
    ) {
        binding.entity = item
        binding.executePendingBindings()
    }
}