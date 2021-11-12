package com.fsdk.faststarted.ui.homepage.home.usefullist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fsdk.faststarted.R
import com.fsdk.faststarted.databinding.ItemListArticleBannerBinding

class AdBannerAdapter(val context: Context) : RecyclerView.Adapter<AdBannerAdapter.ViewHolder>() {

    private val list =
        listOf(R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4, R.mipmap.image5)

    class ViewHolder(val itemViewBinding: ItemListArticleBannerBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListArticleBannerBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemViewBinding.apply {
            Glide.with(context).load(list[position]).into(bannerImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}