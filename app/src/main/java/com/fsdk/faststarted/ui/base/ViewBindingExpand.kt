package com.fsdk.faststarted.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * 通过反射，拿到Activity***DataBinding中的inflate方法
 * @receiver Any
 * @param inflater LayoutInflater
 * @return VB
 */
@Suppress("UNCHECKED_CAST")
inline fun <VB : ViewBinding> Any.getViewBinding(inflater: LayoutInflater): VB {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[0].getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as VB
}

/**
 * 通过反射，拿到Fragment***DataBinding中的inflate方法
 * @receiver Any
 * @param inflater LayoutInflater
 * @param container ViewGroup?
 * @return VB
 */
@Suppress("UNCHECKED_CAST")
inline fun <VB : ViewBinding> Any.getViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?,
    attachToRoot: Boolean = false,
): VB {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
    val inflate = vbClass[0].getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    return inflate.invoke(null, inflater, container, attachToRoot) as VB
}