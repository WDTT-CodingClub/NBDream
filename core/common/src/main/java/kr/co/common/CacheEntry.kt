package kr.co.common

data class CacheEntry<VALUE>(
    val value: VALUE,
    val expiryTime: Long,
)