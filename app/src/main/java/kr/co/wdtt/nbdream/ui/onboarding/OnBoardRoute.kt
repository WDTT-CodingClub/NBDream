package kr.co.wdtt.nbdream.ui.onboarding

import androidx.compose.runtime.Composable

@Composable
fun OnBoardRoute(
    onKakaoClick: () -> Unit,
    onNaverClick: () -> Unit,
    onGoogleClick: () -> Unit,
) {
   OnBoardScreen(
       onKakaoClick = onKakaoClick,
       onNaverClick = onNaverClick,
       onGoogleClick = onGoogleClick
   )
}

@Composable
private fun OnBoardScreen(
    onKakaoClick: () -> Unit,
    onNaverClick: () -> Unit,
    onGoogleClick: () -> Unit
) {


}
