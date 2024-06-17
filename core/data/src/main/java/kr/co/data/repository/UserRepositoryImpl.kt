package kr.co.data.repository

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.data.source.local.UserLocalDataSource
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource,
    private val session: SessionLocalDataSource
) {

    private fun getUser() =
        session.fetchUserId()
            .filterNotNull()
            .flatMapLatest {
                local.fetch(it)
            }
}