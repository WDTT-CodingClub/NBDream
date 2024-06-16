package kr.co.main.my.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Defaultprofile
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageProfileEditRoute(
    popBackStack: () -> Unit,
    viewModel: MyPageProfileEditViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageProfileEditScreen(
        state = state,
        popBackStack = popBackStack,
    )
}

@Composable
private fun MyPageProfileEditScreen(
    popBackStack: () -> Unit,
    state: MyPageProfileEditViewModel.State = MyPageProfileEditViewModel.State(),
) {
    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = stringResource(R.string.feature_main_profile_edit),
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = stringResource(R.string.feature_main_profile_edit_complete),
                            style = MaterialTheme.typo.body2,
                            color = MaterialTheme.colors.gray3
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding)
                .padding(top = 52.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier.size(88.dp),
                model = "",
                contentDescription = stringResource(R.string.feature_main_profile_edit_image),
                placeholder = rememberVectorPainter(image = DreamIcon.Defaultprofile)
            )

            Spacer(modifier = Modifier.height(71.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.feature_main_profile_edit_nick_name),
                    style = MaterialTheme.typo.h4,
                    color = MaterialTheme.colors.gray1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "기존 닉네임",
                    onValueChange = {},
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor =  Color.Transparent,
                        unfocusedContainerColor =  Color.Transparent,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.feature_main_profile_edit_my_adrress),
                    style = MaterialTheme.typo.h4,
                    color = MaterialTheme.colors.gray1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "기존 농장 주소지",
                    onValueChange = {},
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor =  Color.Transparent,
                        unfocusedContainerColor =  Color.Transparent,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageProfileEditScreen(popBackStack = { /*TODO*/ })
    }
}