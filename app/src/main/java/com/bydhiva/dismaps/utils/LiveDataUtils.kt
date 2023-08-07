package com.bydhiva.dismaps.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) = MediatorLiveData<T>().also { mld ->

    val source = this
    var job: Job? = null

    mld.addSource(source) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(duration)
            mld.value = source.value
        }
    }
}

class CombinedLiveData<T, K, S>(source1: LiveData<T>, source2: LiveData<K>, private val combine: (data1: T?, data2: K?) -> S) : MediatorLiveData<S>() {

    private var data1: T? = null
    private var data2: K? = null

    init {
        super.addSource(source1) {
            data1 = it
            value = combine(data1, data2)
        }
        super.addSource(source2) {
            data2 = it
            value = combine(data1, data2)
        }
    }
}