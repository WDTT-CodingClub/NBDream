package kr.co.wdtt.nbdream.data.mapper

sealed class EntityWrapper<T> {
    data class Success<T>(val data: T): EntityWrapper<T>()
    data class Fail<T>(val throwable: Throwable?): EntityWrapper<T>()
}
