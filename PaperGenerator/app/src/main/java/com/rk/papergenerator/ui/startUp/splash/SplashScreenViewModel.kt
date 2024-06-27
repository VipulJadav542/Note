package com.rk.papergenerator.ui.startUp.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rk.papergenerator.ui.navigation.AppScreens
import com.rk.papergenerator.ui.navigation.NavigationCommand
import com.rk.papergenerator.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel
@Inject
constructor(
    private val navigator: Navigator
) : ViewModel() {
    fun navigateToNextScreen() {
        if (isUserSignedIn()) {
            navigator.navigate(NavigationCommand.ToAndClear(AppScreens.EditUserProfileScreen.route))
        } else {
            navigator.navigate(NavigationCommand.ToAndClear(AppScreens.IntroScreen.route))
        }

    }
}

fun isUserSignedIn(): Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}