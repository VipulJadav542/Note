package com.rk.papergenerator.ui.startUp.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rk.papergenerator.ui.navigation.AppScreens
import com.rk.papergenerator.ui.navigation.NavigationCommand
import com.rk.papergenerator.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel
@Inject
constructor(private val navigator: Navigator) : ViewModel() {

    fun performLogin() {

    }

    fun firebaseAuthWithGoogle(
        idToken: String,
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    showToast(context, "signing successfully", Toast.LENGTH_SHORT)
                    navigateToNextScreen()
                } else {
//                    showToast(context, "something went wrong!!!", Toast.LENGTH_SHORT)
                }
            }
            .addOnFailureListener {
//                showToast(context, "something went wrong!!!", Toast.LENGTH_SHORT)
            }
    }

    fun navigateToNextScreen() {
        navigator.navigate(NavigationCommand.ToAndClear(AppScreens.EditUserProfileScreen.route))
    }
}