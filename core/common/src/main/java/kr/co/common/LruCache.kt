package kr.co.common

import kr.co.common.CacheEntry

class LruCache<KEY, VALUE>(
    private val maxSize: Int,
    private val expiryDuration: Long
) : LinkedHashMap<KEY, CacheEntry<VALUE>>(maxSize, 0.75f, true) {

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<KEY, CacheEntry<VALUE>>?): Boolean {
        return size > maxSize
    }

    fun getValidValue(key: KEY): VALUE? {
        if (this[key] != null && this[key]?.expiryTime!! > System.currentTimeMillis()) {
            return this[key]?.value
        } else {
            remove(key)
            return null
        }
    }

    fun putValue(key: KEY, value: VALUE) {
        this[key] = CacheEntry(value, System.currentTimeMillis() + expiryDuration)
    }
}
