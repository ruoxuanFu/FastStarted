package com.fsdk.faststarted.eventbus.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.fsdk.faststarted.eventbus.core.EventObserverWrapper

class EventLiveData<T> : MutableLiveData<T>() {

    fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
        observe(owner, wrapObserver(sticky, observer))
    }

    private fun wrapObserver(sticky: Boolean, observer: Observer<in T>): Observer<T> {
        return EventObserverWrapper(this, sticky, observer)
    }
}