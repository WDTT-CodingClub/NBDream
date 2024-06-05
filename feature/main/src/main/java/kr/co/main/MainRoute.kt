package kr.co.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

internal enum class MainBottomRoute(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME("home", "Home", Icons.Filled.Home),
    CALENDAR("calendar", "Calendar", Icons.Filled.DateRange),
    ACCOUNT("account", "Account", Icons.Filled.Warning),
    COMMUNITY("community", "Community", Icons.Filled.Person),
    MY_PAGE("myPage", "MyPage", Icons.Filled.Person)
}

@Composable
fun MainRoute(
    mainBuilder: NavGraphBuilder.() -> Unit,
) {
    val mainNavController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Scaffold(
            containerColor = Color.Unspecified,
            bottomBar =  {}
        ) { scaffoldPadding ->
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                navController = mainNavController,
                startDestination = MainBottomRoute.HOME.route,
                builder = mainBuilder
            )
        }
    }
}