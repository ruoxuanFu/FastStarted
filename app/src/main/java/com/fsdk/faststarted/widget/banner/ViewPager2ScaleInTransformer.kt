package com.fsdk.faststarted.widget.banner

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * banner 滑动动画
 * @property minScale Float
 * @property defCenter Float
 * @constructor
 */
class ViewPager2ScaleInTransformer(private val minScale: Float = 0.85f) :
    ViewPager2.PageTransformer {

    private val defCenter = 0.5f

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        val pageHeight = page.height

        page.pivotY = (pageHeight shr 1).toFloat()
        page.pivotX = (pageWidth shr 1).toFloat()

        if (position < -1) {
            // [-Infinity,-1)
            // 此页面位于屏幕左侧
            page.scaleX = minScale
            page.scaleY = minScale
            page.pivotX = pageWidth.toFloat()
        } else if (position <= 1) {
            // [-1,1]
            //修改默认幻灯片过渡以缩小页面
            if (position < 0) {
                //1-2:1[0,-1] ;2-1:1[-1,0]
                val scaleFactor: Float = (1 + position) * (1 - minScale) + minScale
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.pivotX = pageWidth * (defCenter + defCenter * -position)
            } else {
                //1-2:2[1,0] ;2-1:2[0,1]
                val scaleFactor: Float = (1 - position) * (1 - minScale) + minScale
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.pivotX = pageWidth * ((1 - position) * defCenter)
            }
        } else {
            // (1,+Infinity]
            page.pivotX = 0f
            page.scaleX = minScale
            page.scaleY = minScale
        }
    }
}