package kr.co.main.my.setting.verify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kr.co.domain.model.AuthType
import kr.co.main.R
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamSocialButton

@Composable
internal fun MyPageSettingDeleteSocialVerifyRoute(
    viewModel: MyPageSettingDeleteSocialVerifyViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    navigateToPrivacyPolicy: () -> Unit = {},
    navigateToOnBoard: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.showNextScreen.collectLatest {
            navigateToOnBoard()
        }
    }

    MyPageSettingDeleteSocialVerifyScreen(
        state = state,
        onVerifyClick = viewModel::onVerifyClick,
        popBackStack = popBackStack,
        navigateToPrivacyPolicy = navigateToPrivacyPolicy
    )
}

@Composable
private fun MyPageSettingDeleteSocialVerifyScreen(
    state: MyPageSettingDeleteSocialVerifyViewModel.State = MyPageSettingDeleteSocialVerifyViewModel.State(),
    onVerifyClick: () -> Unit = {},
    popBackStack: () -> Unit = {},
    navigateToPrivacyPolicy: () -> Unit = {},
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
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding)
                .padding(top = 52.dp)
        ) {
            Text(
                text = "계정 인증이 필요합니다",
                style = MaterialTheme.typo.h2,
                color = MaterialTheme.colors.gray1
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "가입하신 정보는 ...",
                style = MaterialTheme.typo.body1,
                color = MaterialTheme.colors.gray3
            )

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DreamSocialButton(
                    type = AuthType.KAKAO.ordinal,
                    isLogin = false,
                    onClick = {}
                )

                Row(
                    modifier = Modifier
                        .noRippleClickable(navigateToPrivacyPolicy)
                        .semantics {
                            contentDescription = "개인정보 처리방침 이동"
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .semantics {  },
                        text = "개인정보 처리방침",
                        style = MaterialTheme.typo.label,
                        color = MaterialTheme.colors.gray3
                    )

                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .semantics { },
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colors.gray5
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageSettingDeleteSocialVerifyScreen()
    }
}