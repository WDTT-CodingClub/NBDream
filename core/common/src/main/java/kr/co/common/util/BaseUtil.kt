package kr.co.common.util

import android.content.Context

abstract class BaseUtil() {
    protected lateinit var applicationContext: Context

    open fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }
}
