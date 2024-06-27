package com.rk.papergenerator.ui.startUp.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rk.papergenerator.R
import com.rk.papergenerator.ui.theme.PaperAppTheme
@Composable
fun IntroScreen(
    navigateToNextScreen: () -> Unit
) {
    var buttonEnabled by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_intro_creen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, PaperAppTheme.colors.overlayDark80)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(600.dp)
                .fillMaxWidth()
                .background(gradientBrush)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
//                text = "Unleash Your Creativity: \nExplore Endless Paper Possibilities",
                text = "Unlock Unlimited Learning Paths - Your Exam Paper Journey Starts Here",
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = PaperAppTheme.colors.surface,
                style = PaperAppTheme.textStyles.medium.extraLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var checked by remember { mutableStateOf(false) }
                Checkbox(
                    checked = checked,
                    onCheckedChange = { newChecked ->
                        checked = newChecked
                        buttonEnabled = newChecked
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PaperAppTheme.colors.primary,
                        uncheckedColor = PaperAppTheme.colors.surface
                    )
                )
                Text(text = "Accept terms and conditions", color = PaperAppTheme.colors.surface)
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .background(
                        color = PaperAppTheme.colors.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.CenterHorizontally)
                    .clickable {  }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google",
                        modifier = Modifier.padding(start = 80.dp)
                    )
                    Text(
                        "Sign in with Google",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        color = PaperAppTheme.colors.onPrimary,
                        style = PaperAppTheme.textStyles.medium.large
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun IntroPreview() {
    IntroScreen {

    }
}


