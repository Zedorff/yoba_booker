package com.zedorff.yobabooker.app.extensions

import android.arch.lifecycle.LifecycleOwner
import com.zedorff.yobabooker.app.listeners.CoroutineLifecycleListener
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

fun <T> LifecycleOwner.executeAsync(function: () -> T): Deferred<T> {
    val deferred = async(context = CommonPool, start = CoroutineStart.LAZY) {
        function()
    }
    lifecycle.addObserver(CoroutineLifecycleListener(deferred))
    return deferred
}

infix fun <T> Deferred<T>.then(function: (T) -> Unit): Job {
    return launch(context = UI) {
        function(this@then.await())
    }
}