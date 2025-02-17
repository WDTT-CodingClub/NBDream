package kr.co.domain.usecase

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kr.co.common.LruCache

abstract class CachedSuspendUseCase<PARAMS, RESULT>(
    cacheSize: Int,
    cacheDuration: Long = Long.MAX_VALUE,
) : SuspendUseCase<PARAMS, RESULT>() {

    private val cache = LruCache<Any, RESULT>(cacheSize, cacheDuration)
    private val mutex = Mutex()
    @Volatile
    private var isExecuting = false

    override suspend fun invoke(params: PARAMS?): RESULT =
        mutex.withLock {
            if (isExecuting) throw IllegalStateException("Already executing")
            isExecuting = true

            try {
                val key = params?.toString() ?: this::class
                cache.getValidValue(key) ?: build(params).also {
                    cache.putValue(key, it)
                }
            } finally {
                isExecuting = false
            }
        }
}