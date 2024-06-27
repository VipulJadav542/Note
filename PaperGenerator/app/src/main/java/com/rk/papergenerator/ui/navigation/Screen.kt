package com.rk.papergenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

data class Screen(
    val route : String,
    val content: @Composable (NavBackStackEntry) -> Unit
)