package kr.co.onboard.address

import android.os.Parcelable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class InputAddressViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dataStore: DataStore<Preferences>
): BaseViewModel<InputAddressViewModel.State>(savedStateHandle) {
    data class State(
        val fullRoadAddr: String = "",
        val jibunAddr: String = ""
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    fun updateAddresses(fullRoadAddr: String, jibunAddr: String) {
        updateState {
            copy(fullRoadAddr = fullRoadAddr, jibunAddr = jibunAddr)
        }
        viewModelScope.launch {
            saveAddresses(fullRoadAddr, jibunAddr)
        }
    }

    private suspend fun saveAddresses(fullRoadAddr: String, jibunAddr: String) {
        dataStore.edit { preferences ->
            preferences[KEY_FULL_ROAD_ADDR] = fullRoadAddr
            preferences[KEY_JIBUN_ADDR] = jibunAddr
        }
    }

    suspend fun loadAddress() {
        dataStore.data.collect { preferences ->
            val fullRoadAddr = preferences[KEY_FULL_ROAD_ADDR] ?: ""
            val jibunAddr = preferences[KEY_JIBUN_ADDR] ?: ""
            updateState {
                copy(fullRoadAddr = fullRoadAddr, jibunAddr = jibunAddr)
            }
        }
    }

    companion object {
        private val KEY_FULL_ROAD_ADDR = stringPreferencesKey("full_road_addr")
        private val KEY_JIBUN_ADDR = stringPreferencesKey("jibun_addr")
    }
}