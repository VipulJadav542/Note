package com.rk.papergenerator.ui.startUp.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rk.papergenerator.R
import com.rk.papergenerator.ui.theme.PaperAppTheme

@Composable
fun SplashScreen(
    navigateToNextScreen: () -> Unit,
) {

    val drawable = painterResource(id = R.drawable.app_logo)

//    LaunchedEffect(key1 = Unit) {
//        delay(2000)
//        navigateToNextScreen()
//    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = PaperAppTheme.colors.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 180.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(
                painter = drawable, contentDescription = "logo",
                tint = PaperAppTheme.colors.surfaceTint
            )
            Text(
                text = "Paper Generator",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 50.dp),
                text = "let's simplify your work",
                fontFamily = FontFamily.SansSerif,
            )

            Box(modifier = Modifier.padding(top = 40.dp))
            Text(
                text = "By",
            )
            Text(
                text = "Dhvanil InfoTech",
            )

        }
    }
}


@Preview
@Composable
fun SplashPreview(){
    SplashScreen {

    }
}


