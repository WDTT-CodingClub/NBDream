package kr.co.main.my.profile

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.co.domain.entity.UserEntity
import kr.co.domain.usecase.image.UploadImageUseCase
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.domain.usecase.user.RegisterUserUseCase
import kr.co.domain.usecase.user.SaveUserLocalUseCase
import kr.co.domain.usecase.validate.ValidateNameUseCase
import kr.co.ui.base.BaseViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class MyPageProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveUserLocalUseCase: SaveUserLocalUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val validateNameUseCase: ValidateNameUseCase
) : BaseViewModel<MyPageProfileEditViewModel.State>(savedStateHandle) {

    fun onNameChanged(name: String) = updateState {
        copy(name = name)
    }

    fun onAddressChanged(address: String) = updateState {
        copy(address = address)
    }

    private fun onImageChanged(url: String) = updateState {
        copy(profileImageUrl = url)
    }

    fun uploadImage(image: File) = loadingScope {
        uploadImageUseCase(
            UploadImageUseCase.Params(
                domain = DOMAIN,
                file = image
            )
        ).let { url ->
            onImageChanged(url)
        }
    }

    fun onClickConfirm() = loadingScope {
        validateNameUseCase(currentState.name).run {
            updateState {
                copy(nameValid = this@run)
            }
            if (this) {
                registerUserUseCase(
                    UserEntity(
                        name = currentState.name!!,
                        profileImage = currentState.profileImageUrl,
                        address = currentState.address
                    )
                )
            } else {
                throw IllegalArgumentException("InValid")
            }
        }
    }.invokeOnCompletion {
        if (it == null)
        viewModelScopeEH.launch {
            saveUserLocalUseCase(
                UserEntity(
                    name = currentState.name!!,
                    profileImage = currentState.profileImageUrl,
                    address = currentState.address
                )
            )
        }
    }

    init {
        viewModelScopeEH.launch {
            fetchUserUseCase.invoke()
                .collectLatest {
                    onNameChanged(it.name)
                    onAddressChanged(it.address ?: "")
                    onImageChanged(it.profileImage ?: "")
                }
        }
    }

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val name: String? = null,
        val nameValid: Boolean = false,
        val profileImageUrl: String? = null,
        val address: String? = null,
    ) : BaseViewModel.State {
        val nameGuide: String?
            get() = when {
                name.isNullOrBlank() -> "이름을 입력해주세요"
                name.length < 2 || name.length > 12 -> "이름은 최소 2글자 이상 12글자 이하 이어야 합니다"
                !nameValid -> "이름은 영문, 한글, 숫자만 가능합니다"
                else -> null
            }
    }

    private companion object {
        const val DOMAIN = "profile"
    }
}
