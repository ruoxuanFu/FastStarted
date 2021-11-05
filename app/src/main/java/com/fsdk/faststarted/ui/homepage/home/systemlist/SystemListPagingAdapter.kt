package com.fsdk.faststarted.ui.homepage.home.systemlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fsdk.faststarted.bean.SystemListData
import com.fsdk.faststarted.databinding.ItemListSystemBinding

class SystemListPagingAdapter :
    PagingDataAdapter<SystemListData, SystemListPagingAdapter.ViewHolder>(DataDiffCallback) {

    object DataDiffCallback : DiffUtil.ItemCallback<SystemListData>() {
        /**
         * 如果两个项目代表相同的对象，则为真，如果它们不同，则为假。
         */
        override fun areItemsTheSame(oldItem: SystemListData, newItem: SystemListData): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * 如果项目的内容相同则为真，如果不同则为假。
         */
        override fun areContentsTheSame(oldItem: SystemListData, newItem: SystemListData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class ViewHolder(private val itemViewBinding: ItemListSystemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(item: SystemListData?) {
            itemViewBinding.apply {
                if (item == null) {
                    tvSystemNo.text = "未知"
                    tvSystemName.text = "未知"
                } else {
                    tvSystemNo.text = "${item.id}"
                    tvSystemName.text = item.name
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListSystemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}