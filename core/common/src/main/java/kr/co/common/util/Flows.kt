package kr.co.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6
    )
}

fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7
    )
}

fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8
    )
}

inline fun <reified T> combineNullableFlows(vararg flows: Flow<T?>): Flow<List<T?>> =
    flow {
        val latest = Array<T?>(flows.size) { null }
        combine(*flows) { values ->
            values.forEachIndexed { index, value ->
                latest[index] = value
            }
            emit(latest.toList())
        }
    }

inline fun <reified T> Array<out Flow<T?>>.nullableCombine(): Flow<List<T?>> = flow {
    val latest = Array<T?>(this@nullableCombine.size) { null }
    combine(*this@nullableCombine) { values ->
        values.forEachIndexed { index, value ->
            latest[index] = value
        }
        emit(latest.toList())
    }
}

fun <T1, T2, R> Flow<T1?>.nullableCombine(flow: Flow<T2?>, transform: (T1?, T2?) -> R): Flow<R> = flow {
    combine(this@nullableCombine, flow) { t1, t2 ->
        transform(t1, t2)
    }.collect { value ->
        emit(value)
    }
}