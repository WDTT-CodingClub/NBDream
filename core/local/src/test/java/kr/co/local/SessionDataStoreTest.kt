package kr.co.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kr.co.data.source.local.SessionLocalDataSource
import org.junit.Before
import kotlin.test.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("UNCHECKED_CAST")
class SessionDataStoreTest {

    @MockK
    private lateinit var dataStore: DataStore<Preferences>

    @InjectMockKs
    private lateinit var sessionLocalDataSourceImpl: SessionLocalDataSourceImpl

    private lateinit var sessionLocalDataSource: SessionLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sessionLocalDataSource = sessionLocalDataSourceImpl

        coEvery { dataStore.updateData(any()) } coAnswers {
            val updateBlock = it.invocation.args[0] as suspend (Preferences) -> Preferences
            val mockPreferences = mockk<MutablePreferences>(relaxed = true)
            updateBlock(mockPreferences)
            mockPreferences
        }
    }

    @Test
    fun `access token 저장`() = runTest {
        val accessToken = "test"

        sessionLocalDataSource.updateAccessToken(accessToken)

        coVerify { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) }
    }

    @Test
    fun `access token 반환 로직`() = runTest {
        val accessToken = "test"
        val preferencesKey = stringPreferencesKey("access_token")

        val mockPreferences = mockk<Preferences> {
            every { this@mockk[preferencesKey] } returns accessToken
        }

        every { dataStore.data } returns flowOf(mockPreferences)

        val result = sessionLocalDataSourceImpl.getAccessToken()
        assertEquals(accessToken, result)
    }

    @Test
    fun `데이터 스토어 데이터 삭제`() = runTest {
        listOf("1","2","3").forEach {
            sessionLocalDataSource.updateAccessToken(it)
        }

        sessionLocalDataSource.removeAll()

        assertEquals(sessionLocalDataSource.getAccessToken(), null)
    }
}