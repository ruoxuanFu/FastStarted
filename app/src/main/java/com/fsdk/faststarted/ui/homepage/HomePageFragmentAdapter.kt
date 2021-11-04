package com.fsdk.faststarted.ui.homepage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePageFragmentAdapter(
    fm: FragmentManager,
    lc: Lifecycle,
    private val fragments: List<Fragment>
) :
    FragmentStateAdapter(fm, lc) {
    /**
     * 返回适配器保存的数据集中的项目总数
     *
     * @return 此适配器中的项目总数
     */
    override fun getItemCount(): Int {
        return fragments.size
    }

    /**
     * 提供与指定位置关联的新Fragment
     * 适配器将负责 Fragment 生命周期：
     *
     *  * Fragment 将用于显示一个项目
     *  * 当 Fragment 离视口太远时，它会被销毁，并且它的状态将被保存。
     *  当 item 再次靠近视口时，将请求一个新的 Fragment，并使用之前保存的状态对其进行初始化
     */
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}