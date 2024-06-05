package kr.co.wdtt.nbdream.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.co.wdtt.nbdream.ui.onboarding.login.AddressSelectionListener
import kr.co.wdtt.nbdream.ui.onboarding.login.LocationSearchScreen
import kr.co.wdtt.nbdream.ui.onboarding.login.LocationSearchWebViewScreen

@Composable
fun DreamApp() {
    val navController = rememberNavController()
    var fullRoadAddr by remember { mutableStateOf("") }
    var jibunAddr by remember { mutableStateOf("") }

    NavHost(navController, startDestination = "location_search") {
        composable("location_search") {
            LocationSearchScreen(
                navController = navController,
                initialFullRoadAddr = fullRoadAddr,
                initialJibunAddr = jibunAddr,
                onAddressSelected = { roadAddr, jibun ->
                    fullRoadAddr = roadAddr
                    jibunAddr = jibun
                }
            )
        }
        composable("webview") {
            LocationSearchWebViewScreen(navController = navController, addressSelectionListener = object : AddressSelectionListener {
                override fun onAddressSelected(fullRoadAddr: String, jibunAddr: String) {
                    navController.previousBackStackEntry?.savedStateHandle?.set("fullRoadAddr", fullRoadAddr)
                    navController.previousBackStackEntry?.savedStateHandle?.set("jibunAddr", jibunAddr)
                    navController.popBackStack()
                }
            })
        }
    }

//    SelectCropScreen()
//    LocationSearchScreen()
//    Login()
//    HomeRoute()
}