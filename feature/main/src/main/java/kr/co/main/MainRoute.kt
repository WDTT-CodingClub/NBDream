package kr.co.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dreamicon.Account
import dreamicon.Calendar
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.ext.shadow
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Community
import kr.co.ui.icon.dreamicon.Home
import kr.co.ui.icon.dreamicon.Mypage
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors

internal enum class MainBottomRoute(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME("home", "Home", DreamIcon.Home),
    CALENDAR("calendar", "Calendar", DreamIcon.Calendar),
    COMMUNITY("community", "Community", DreamIcon.Community),
    ACCOUNT("account", "Account", DreamIcon.Account),
    MY_PAGE("myPage", "MyPage", DreamIcon.Mypage)
}

@SuppressLint("StaticFieldLeak")
internal object MainNav {
    lateinit var controller: NavController

    fun setNavController(navController: NavController) {
        controller = navController
    }
}

@Composable
internal fun MainRoute(
    mainBuilder: NavGraphBuilder.() -> Unit,
) {
    val mainNavController = rememberNavController()
    MainNav.setNavController(mainNavController)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Scaffold(
            containerColor = MaterialTheme.colors.gray9,
            bottomBar = {
                MainBottomBar(
                    mainNavController = mainNavController
                )
            }
        ) { scaffoldPadding ->
            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .scaffoldBackground(
                        scaffoldPadding = scaffoldPadding,
                        padding = PaddingValues(0.dp)
                    ),
                navController = mainNavController,
                startDestination = MainBottomRoute.HOME.route,
                builder = mainBuilder
            )
        }
    }
}

@Composable
private fun MainBottomBar(
    mainNavController: NavController
) {
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .shadow(
                color = MaterialTheme.colors.black.copy(alpha = 0.08f),
                blurRadius = 12.dp,
                offsetY = 2.dp,
                offsetX = 2.dp
            ),
        containerColor = MaterialTheme.colors.white
    ) {
        MainBottomRoute.entries.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    mainNavController.navigate(screen.route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colors.primary4,
                    unselectedIconColor = MaterialTheme.colors.grey4,
                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        LocalAbsoluteTonalElevation.current
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun MainBottomScreenPreview() {
    NBDreamTheme {
        MainBottomBar(mainNavController = rememberNavController())
    }
}