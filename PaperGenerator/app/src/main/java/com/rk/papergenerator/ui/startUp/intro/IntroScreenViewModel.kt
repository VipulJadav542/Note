package com.rk.papergenerator.ui.startUp.intro

import androidx.lifecycle.ViewModel
import com.rk.papergenerator.ui.navigation.AppScreens
import com.rk.papergenerator.ui.navigation.NavigationCommand
import com.rk.papergenerator.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroScreenViewModel
@Inject
constructor(
    private val navigator: Navigator
) : ViewModel() {
    fun navigateToNextScreen() {
        navigator.navigate(NavigationCommand.ToAndClear(AppScreens.LoginScreen.route))
    }
}