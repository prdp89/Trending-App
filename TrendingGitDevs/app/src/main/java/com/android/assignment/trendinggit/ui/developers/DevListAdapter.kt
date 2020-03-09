package com.android.assignment.trendinggit.ui.developers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.ItemTrendingDevBinding
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.vo.databoundadapter.DataBoundListAdapter

class DevListAdapter(
    appExecutors: AppExecutors,
    private val callback: ((TrendingDevEntity) -> Unit)?
) :
    DataBoundListAdapter<TrendingDevEntity,
            ItemTrendingDevBinding>(mAppExecutors = appExecutors,
        mDiffCallback = object : DiffUtil.ItemCallback<TrendingDevEntity>() {
            override fun areItemsTheSame(
                oldItem: TrendingDevEntity,
                newItem: TrendingDevEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrendingDevEntity,
                newItem: TrendingDevEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTrendingDevBinding {

        val binding = DataBindingUtil.inflate<ItemTrendingDevBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_dev,
            parent,
            false
        )

        binding.root.setOnClickListener {
            callback?.invoke(binding.entity!!)
        }

        return binding
    }

    override fun bind(
        binding: ItemTrendingDevBinding,
        item: TrendingDevEntity,
        position: Int
    ) {
        binding.entity = item
    }
}