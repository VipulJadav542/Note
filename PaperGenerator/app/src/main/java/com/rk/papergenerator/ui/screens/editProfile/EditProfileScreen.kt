package com.rk.papergenerator.ui.screens.editProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rk.papergenerator.R
import com.rk.papergenerator.ui.screens.editProfile.model.UserProfile
import com.rk.papergenerator.ui.theme.PaperAppTheme

@Composable
fun EditUserProfileScreen(
    navigateToNextScreen: (UserProfile) -> Unit,
    backPressed: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            EditProfileScreen(
                onSaveProfile = ::saveProfile,
                onNavigateBack = ::navigateBack
            )
        }
    }
}

fun navigateBack() {
    // Implement navigation logic to go back to the previous screen
}

fun saveProfile(name: String, email: String) {
    // Implement logic to save the profile changes
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onSaveProfile: (String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var userName by remember { mutableStateOf(TextFieldValue()) }
    var name by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var organizationName by remember { mutableStateOf(TextFieldValue()) }
    var mobileNumber by remember { mutableStateOf(TextFieldValue()) }
    var whatsAppNumber by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(PaperAppTheme.colors.inversePrimary),
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_default),
                        contentDescription = "Back",
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Box {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_gallery),
                                contentDescription = "Icon",
                                tint = Color.Blue,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Icon",
                                tint = Color.Blue,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("User Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )

                OutlinedTextField(
                    value = organizationName,
                    onValueChange = { organizationName = it },
                    label = { Text("Organization Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    label = { Text("Mobile Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                OutlinedTextField(
                    value = whatsAppNumber,
                    onValueChange = { whatsAppNumber = it },
                    label = { Text("WhatsApp Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                Button(
                    onClick = { onSaveProfile(name.text, email.text) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Profile")
                }
            }
        }
    )
}

@Preview
@Composable
fun ViewEditProfile() {
    EditUserProfileScreen(navigateToNextScreen = { userProfile ->

    }, backPressed = {

    })
}