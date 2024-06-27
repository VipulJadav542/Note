package com.rk.papergenerator.ui.navigation

import com.rk.papergenerator.ui.screens.dashboard.DashBoardScreen
import com.rk.papergenerator.ui.screens.editProfile.EditUserProfileScreen
import com.rk.papergenerator.ui.startUp.intro.IntroScreen
import com.rk.papergenerator.ui.startUp.login.LoginScreen
import com.rk.papergenerator.ui.startUp.splash.SplashScreen

sealed class AppScreens(val route: String) {

    object SplashScreen : AppScreens("SplashScreen")

    object IntroScreen : AppScreens("IntroScreen")

    object LoginScreen : AppScreens("LoginScreen")

    object EditUserProfileScreen : AppScreens("EditUserProfileScreen")

    object DashBoardScreen : AppScreens("DashBoardScreen")

}

val screens = listOf(
    Screen(AppScreens.SplashScreen.route) { SplashScreen {

    } },
    Screen(AppScreens.IntroScreen.route) { IntroScreen {

    } },
    Screen(AppScreens.LoginScreen.route) { LoginScreen {

    } },
    Screen(AppScreens.DashBoardScreen.route) { DashBoardScreen() },
//    Screen(AppScreens.EditUserProfileScreen.route) { EditUserProfileScreen },
)