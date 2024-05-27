package kr.co.wdtt.nbdream.data.remote.retrofit

import kr.co.wdtt.nbdream.data.remote.api.NetworkApi

interface NetworkFactoryManager {
    fun create(
        baseUrl: String,
        headAuth: String?=null,
        headKey: String?=null
    ): NetworkApi
}
