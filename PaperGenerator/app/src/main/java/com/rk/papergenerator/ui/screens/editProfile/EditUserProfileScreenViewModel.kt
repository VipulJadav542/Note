package com.rk.papergenerator.ui.screens.editProfile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rk.papergenerator.ui.navigation.AppScreens
import com.rk.papergenerator.ui.navigation.NavigationCommand
import com.rk.papergenerator.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditUserProfileScreenViewModel
@Inject
constructor(
    val navigator: Navigator
) : ViewModel() {
    fun navigateToNextScreen() {
        FirebaseAuth.getInstance().signOut()
        navigator.navigate(NavigationCommand.ToAndClear(AppScreens.LoginScreen.route))
    }
}