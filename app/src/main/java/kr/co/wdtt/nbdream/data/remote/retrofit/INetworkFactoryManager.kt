package kr.co.wdtt.nbdream.data.remote.retrofit

import kr.co.wdtt.nbdream.data.remote.api.NetworkApi

interface INetworkFactoryManager {
    fun create(baseUrl: String, headAuth: String, headKey: String): NetworkApi
}
