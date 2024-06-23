package kr.co.main.home.chat

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.co.common.model.CustomException
import kr.co.domain.usecase.SendAiChatUseCase
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val sendAiChatUseCase: SendAiChatUseCase,
) : BaseViewModel<ChatViewModel.State>(savedStateHandle) {

    fun onSelectedChat(e: Int) {
        when (e) {
            1 -> {
                updateState {
                    copy(chats = this.chats + (false to "내 토지는 작물 재배에 적합 할까?"))
                }

                loadingScope {
                    sendAiChatUseCase("1").also {
                        updateState {
                            copy(
                                chats = this.chats + (true to it)
                                        + (true to "저에게 더 물어보고 싶은게 있으신가요?"),
                            )
                        }
                    }
                }
            }

            2 -> {
                updateState {
                    copy(
                        chats = this.chats + (false to "작물별로 어떤 토양이 적합한지 궁금해!")
                                + (true to "어떤 작물이 궁금하신가요? \n 작물을 선택해주세요"),
                        chatType = 1
                    )
                }
            }
        }
    }

    fun onSelectedCrop(e: String) {
        updateState {
            copy(
                chats = this.chats + (false to e),
                chatType = 0
            )
        }

        loadingScope {
            sendAiChatUseCase(e).also {
                updateState {
                    copy(
                        chats = this.chats + (true to it)
                                + (true to "저에게 더 물어보고 싶은게 있으신가요?"),
                    )
                }
            }
        }
    }

    init {
        loadingScope {
            fetchUserUseCase().first().also {
                updateState {
                    copy(
                        userName = it.name,
                        chats = listOf(true to "안녕하세요 ${it.name}님의 토지친구 토봇입니다^^ \n 궁금한 내용을 선택해 주세요!")
                    )
                }
            }
        }

        viewModelScopeEH.launch {
            error.collect { throwable ->
                when (throwable.customError?.errorCode) {
                    404 -> updateState {
                        copy(chats = this.chats + (true to "토양 정보가 없습니다 \n주소 등록 후 이용해주세요")
                                + (true to "저에게 더 물어보고 싶은게 있으신가요?"))
                    }

                    else -> updateState {
                        copy(chats = this.chats + (true to "예기치 못한 문제로 분석을 할 수 없습니다.")
                                + (true to "저에게 더 물어보고 싶은게 있으신가요?"))
                    }
                }
            }
        }

        viewModelScopeEH.launch {
            isLoading.collect {
                updateState {
                    copy(isAiLoading = it)
                }
            }
        }
    }


    data class State(
        val userName: String = "",
        val chats: List<Pair<Boolean, String>> = emptyList(),

        val chatType: Int = 0,
        val selectedChat: Int = 0,

        val isAiLoading: Boolean = false,
    ) : BaseViewModel.State {
        val isChatVisible: Boolean
            get() = isAiLoading.not()
    }

    override fun createInitialState(savedState: Parcelable?): State = State()
}
