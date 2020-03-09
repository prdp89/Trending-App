package com.android.assignment.trendinggit.ui.repository.repodetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.ItemTrendingRepoDevBinding
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.vo.databoundadapter.DataBoundListAdapter

class RepoDevListAdapter(
    appExecutors: AppExecutors,
    private val callback: ((TrendingRepoEntity.BuiltBy) -> Unit)?
) :
    DataBoundListAdapter<TrendingRepoEntity.BuiltBy,
            ItemTrendingRepoDevBinding>(mAppExecutors = appExecutors,
        mDiffCallback = object : DiffUtil.ItemCallback<TrendingRepoEntity.BuiltBy>() {
            override fun areItemsTheSame(
                oldItem: TrendingRepoEntity.BuiltBy,
                newItem: TrendingRepoEntity.BuiltBy
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrendingRepoEntity.BuiltBy,
                newItem: TrendingRepoEntity.BuiltBy
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTrendingRepoDevBinding {

        val binding = DataBindingUtil.inflate<ItemTrendingRepoDevBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_repo_dev,
            parent,
            false
        )

        binding.root.setOnClickListener {
            callback?.invoke(binding.entity!!)
        }

        return binding
    }

    override fun bind(
        binding: ItemTrendingRepoDevBinding,
        item: TrendingRepoEntity.BuiltBy,
        position: Int
    ) {
        binding.entity = item
    }
}