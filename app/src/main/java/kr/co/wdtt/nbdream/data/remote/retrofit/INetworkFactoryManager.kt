package kr.co.wdtt.nbdream.data.remote.retrofit

interface INetworkFactoryManager {

    fun create(baseUrl: String): NetWorkRequestFactory
}
