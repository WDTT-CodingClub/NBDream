package kr.co.main.community.temp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.co.main.community.temp.navigation.COMMUNITY_ROUTE
import kr.co.main.community.temp.navigation.bulletinDetailScreen
import kr.co.main.community.temp.navigation.bulletinWritingScreen
import kr.co.main.community.temp.navigation.communityScreen
import kr.co.main.community.temp.navigation.navigateToBulletinDetail
import kr.co.main.community.temp.navigation.navigateToBulletinWriting

@Composable
fun TempCommunityApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = COMMUNITY_ROUTE,
        modifier = modifier,
    ) {
        communityScreen(
            navController::navigateToBulletinWriting,
            {},
            navController::navigateToBulletinDetail,
        )
        bulletinWritingScreen(navController::popBackStack)
        bulletinDetailScreen(navController::popBackStack)
    }
}
