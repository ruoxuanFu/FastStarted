package com.fsdk.faststarted.ui.homepage.home.articlelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fsdk.faststarted.R
import com.fsdk.faststarted.databinding.ItemListArticleBannerBinding

class BannerAdapter(val context: Context) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    companion object {
        //需要增加的页面数量，需是sidePage的二倍
        const val needPage: Int = 4

        //边界页面数量
        const val sidePage: Int = 2
    }

    private val list =
        listOf(R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4, R.mipmap.image5)

    class BannerViewHolder(val itemViewBinding: ItemListArticleBannerBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemListArticleBannerBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.itemViewBinding.apply {
            Glide.with(context).load(list[toRealPosition(position)]).into(bannerImage)
        }
    }

    override fun getItemCount(): Int {
        return getRealCount() + needPage
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(toRealPosition(position))
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(toRealPosition(position))
    }

    fun getRealCount(): Int {
        return list.size
    }

    /**
     * 获取真正的下标
     *
     * 8-1 7-0 6-4 5-3 4-2 3-1 2-0 1-4 0-3
     *
     * @param position Int 当前图片在列表中index：8-1 7-0 6-4 5-3 4-2 3-1 2-0 1-4 0-3
     * @return Int 当前图片在list中的index：       2   1   5   4   3   2   1   5   4
     */
    fun toRealPosition(position: Int): Int {
        var realPosition = 0
        if (getRealCount() > 1) {
            realPosition = (position - sidePage) % getRealCount()
        }
        if (realPosition < 0) {
            realPosition += getRealCount()
        }
        return realPosition
    }
}