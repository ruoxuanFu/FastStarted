package com.fsdk.faststarted.eventbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.fsdk.faststarted.constant.EventName
import com.fsdk.faststarted.eventbus.core.EmptyMessage
import com.fsdk.faststarted.eventbus.core.EventLiveData

object ViewModelBus {

    private val channels = HashMap<String, EventLiveData<*>>()

    private fun <T> with(@EventName eventName: String): EventLiveData<T> {
        synchronized(channels) {
            if (!channels.containsKey(eventName)) {
                channels[eventName] = EventLiveData<T>()
            }
            return (channels[eventName] as EventLiveData<T>)
        }
    }

    fun <T> post(@EventName eventName: String, message: T) {
        val eventLiveData = with<T>(eventName)
        eventLiveData.postValue(message!!)
    }

    fun <T> observe(
        owner: LifecycleOwner,
        @EventName eventName: String,
        sticky: Boolean = false,
        observer: Observer<T>
    ) {
        with<T>(eventName).observe(owner, sticky, observer)
    }

    fun post(@EventName eventName: String) {
        val eventLiveData = with<EmptyMessage>(eventName)
        eventLiveData.postValue(EmptyMessage)
    }

    fun observe(
        owner: LifecycleOwner,
        @EventName eventName: String,
        sticky: Boolean = false,
        observer: () -> Unit
    ) {
        with<EmptyMessage>(eventName).observe(owner, sticky) {
            observer()
        }
    }
}