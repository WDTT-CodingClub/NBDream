package kr.co.main.community.temp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.co.main.community.BulletinDetailScreen
import kr.co.main.community.BulletinWritingRoute
import kr.co.main.community.CommunityScreen


const val COMMUNITY_ROUTE = "community_route"

fun NavController.navigateToCommunity(navOptions: NavOptions? = null) =
    navigate(COMMUNITY_ROUTE, navOptions)

fun NavGraphBuilder.communityScreen(
    onWritingClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onBulletinClick: (String) -> Unit,
) {
    composable(route = COMMUNITY_ROUTE) {
        CommunityScreen(
            onWritingClick = onWritingClick,
            onNotificationClick = onNotificationClick,
            onBulletinClick = onBulletinClick,
        )
    }
}


const val BULLETIN_WRITING_ROUTE = "bulletin_writing_route"

fun NavController.navigateToBulletinWriting(navOptions: NavOptions? = null) =
    navigate(BULLETIN_WRITING_ROUTE, navOptions)

fun NavGraphBuilder.bulletinWritingScreen(
    onBackClick: () -> Unit,
) {
    composable(route = BULLETIN_WRITING_ROUTE) {
        BulletinWritingRoute(
            onBackClick = onBackClick,
        )
    }
}


const val BULLETIN_DETAIL_ROUTE = "bulletin_detail_route"

fun NavController.navigateToBulletinDetail(bulletinId: String, navOptions: NavOptions? = null) =
    navigate(BULLETIN_DETAIL_ROUTE, navOptions)

fun NavGraphBuilder.bulletinDetailScreen(
    onBackClick: () -> Unit,
) {
    composable(route = BULLETIN_DETAIL_ROUTE) {
        BulletinDetailScreen(
            onBackClick = onBackClick,
        )
    }
}
