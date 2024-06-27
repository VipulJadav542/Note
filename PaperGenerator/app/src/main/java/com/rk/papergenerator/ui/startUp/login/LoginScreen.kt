@file:Suppress("DEPRECATION")

package com.rk.papergenerator.ui.startUp.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.rk.papergenerator.R
import com.rk.papergenerator.ui.theme.PaperAppTheme

@Composable
fun LoginScreen(
    firebaseAuthWithGoogle: (String) -> Unit,
) {
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        firebaseAuthWithGoogle(account.idToken!!)
                    }
                } catch (e: ApiException) {
                    Log.d("error", "Error getting")
                }
            }
        }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PaperAppTheme.colors.surface
            )
            .fillMaxSize()
    ) {
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, PaperAppTheme.colors.overlayDark80)
        )
        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .height(500.dp)
            .fillMaxWidth()
            .background(gradientBrush))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.wavy_gen_01_single_07), contentDescription = "null")
            Text(
                text = "Welcome to Paper Generator!",
                textAlign = TextAlign.Center,
                color = PaperAppTheme.colors.surface,
                modifier = Modifier.padding(bottom = 32.dp),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "click here to signing...",
                style = MaterialTheme.typography.labelLarge,
                color = PaperAppTheme.colors.surface,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(onClick = { signInLauncher.launch(mGoogleSignInClient.signInIntent) }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    modifier = Modifier.padding(5.dp)
                        .size(20.dp)
                )

                Text(
                    "Sign in with Google",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W700,
                )

            }

        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen {

    }
}
