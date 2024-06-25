package kr.co.main.my.setting.delete

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.main.R
import kr.co.main.model.my.MyPageDeleteReason
import kr.co.ui.ext.IconDefaults
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.ext.vectorColors
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.CheckCircle
import kr.co.ui.icon.dreamicon.OutLineCircle
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamButton
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamCheckIcon
import kr.co.ui.widget.DreamDialog

@Composable
internal fun MyPageSettingDeleteAccountRoute(
    viewModel: MyPageSettingDeleteAccountViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val (isDialogVisible, setIsDialogVisible) = rememberSaveable {
        mutableStateOf(false)
    }

    MyPageSettingDeleteAccountScreen(
        state = state,
        setSelected = viewModel::onSelectChange,
        setReason = viewModel::onReasonChange,
        popBackStack = popBackStack,
        onDeleteClick = { setIsDialogVisible(true) }
    )

    if (isDialogVisible)
        DreamDialog(
            header = "정말 탈퇴 하시겠어요?",
            description = "탈퇴한 계정은 다시 복구 할 수 없습니다.\n탈퇴를 원한다면 확인 버튼을 눌러주세요",
            confirmText = "탈퇴하기",
            dismissText = "다음에 할래요",
            onConfirm = {
                viewModel.onDeleteClick()
                setIsDialogVisible(false)
            },
            onDismiss = { setIsDialogVisible(false) }
        )
}

@Composable
private fun MyPageSettingDeleteAccountScreen(
    state: MyPageSettingDeleteAccountViewModel.State = MyPageSettingDeleteAccountViewModel.State(),
    setReason: (String) -> Unit = {},
    setSelected: (String?) -> Unit = {},
    popBackStack: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = "회원 탈퇴",
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.feature_main_pop_back_stack)
                        )
                    }
                }
            )
        },
        bottomBar = {
            DreamButton(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
                enabled = state.isSelectValid,
                text = "탈퇴하기",
                onClick = onDeleteClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colors.gray2,
                    contentColor = MaterialTheme.colors.gray10
                )
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .scaffoldBackground(
                    scaffoldPadding = scaffoldPadding,
                    padding = PaddingValues(24.dp)
                )
                .padding(top = 52.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "농부의 꿈을 정말 떠나시는 건가요?",
                    style = MaterialTheme.typo.h2,
                    color = MaterialTheme.colors.gray1
                )

                Text(
                    text = "계정을 삭제하시려는 이유를 말씀해주시면 " +
                            "\n서비스 개선에 중요자료로 활용하겠습니다.",
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.gray3
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                MyPageDeleteReason.entries.forEach { reason ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .clickable { setSelected(reason.text) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DreamCheckIcon(
                            modifier = Modifier.size(24.dp),
                            state = state.reason == reason.text,
                            leftIcon = DreamIcon.OutLineCircle,
                            rightIcon = DreamIcon.CheckCircle,
                            contentDescription = "check reason",
                            onClick = { setSelected(reason.text) },
                            colors = IconDefaults.vectorColors(
                                default = MaterialTheme.colors.gray6,
                            )
                        )
                        Text(
                            text = reason.text,
                            style = MaterialTheme.typo.body1,
                            color = MaterialTheme.colors.gray1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 144.dp),
                value = state.otherReason ?: "",
                onValueChange = {
                    if (it.length <= 200) setReason(it)
                },
                placeholder = {
                    Text(
                        text = "기타 이유를 적어주세요",
                        style = MaterialTheme.typo.body1,
                        color = MaterialTheme.colors.gray5
                    )
                }
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End),
                text = "${state.otherReason?.length}/200",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colors.gray5
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val (selected, setSelected) = rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val (reason, setReason) = rememberSaveable {
        mutableStateOf("")
    }
    NBDreamTheme {
        MyPageSettingDeleteAccountScreen(
            popBackStack = {},
            setSelected = setSelected,
            setReason = setReason

        )
    }
}
