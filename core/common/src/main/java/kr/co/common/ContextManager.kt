package kr.co.common

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ContextManager {

    private var context: Context? = null

    @Synchronized
    fun setContext(context: Context) {
        this.context = context
    }

    @Synchronized
    fun getContext(): Context {
        return context ?: throw IllegalStateException("Context not set")
    }

    @Synchronized
    fun isContextSet(): Boolean {
        return context != null
    }

    @Synchronized
    fun clearContext() {
        context = null
    }
}