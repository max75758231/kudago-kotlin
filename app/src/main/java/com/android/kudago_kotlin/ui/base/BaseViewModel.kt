package com.android.kudago_kotlin.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {
    protected val mainScope = CoroutineScope(Dispatchers.Main)

    /**
     * Coroutines in a Pool of Thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.Default)

    override fun onCleared() {
        super.onCleared()
        mainScope.coroutineContext.cancel()
        ioScope.coroutineContext.cancel()
    }
}