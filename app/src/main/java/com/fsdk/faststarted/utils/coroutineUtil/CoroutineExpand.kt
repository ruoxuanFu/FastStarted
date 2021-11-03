package com.fsdk.faststarted.utils.coroutineUtil

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

///
///协程统一处理逻辑拓展
///

inline fun AppCompatActivity.launchMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(GlobalCoroutineExceptionHandler(errCode, errMsg, report)) {
        block.invoke(this)
    }
}

inline fun AppCompatActivity.launchIO(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(
        Dispatchers.IO + GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

inline fun Fragment.launchMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(GlobalCoroutineExceptionHandler(errCode, errMsg, report)) {
        block.invoke(this)
    }
}

inline fun Fragment.launchIO(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(
        Dispatchers.IO + GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

inline fun ViewModel.launchMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(GlobalCoroutineExceptionHandler(errCode, errMsg, report)) {
        block.invoke(this)
    }
}

inline fun ViewModel.launchIO(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    noinline block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(
        Dispatchers.IO + GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}
