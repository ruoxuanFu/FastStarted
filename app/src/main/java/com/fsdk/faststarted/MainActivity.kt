package com.fsdk.faststarted

import android.content.Intent
import com.fsdk.faststarted.databinding.ActivityMainBinding
import com.fsdk.faststarted.ui.base.BaseActivity
import com.fsdk.faststarted.ui.homepage.HomePageActivity
import com.fsdk.faststarted.utils.coroutineUtil.launchMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val countDownMax = 3

    override fun ActivityMainBinding.initBinding() {
        launchMain {
            flow {
                for (i in 1..countDownMax) {
                    delay(1000)
                    emit(i)
                }
            }.flowOn(Dispatchers.IO)
                .collect {
                    if (it == countDownMax) {
                        startActivity(Intent(this@MainActivity, HomePageActivity::class.java))
                        finish()
                    } else {
                        binding.tvCountdown.text = "${countDownMax - it}"
                    }
                }
        }
    }
}