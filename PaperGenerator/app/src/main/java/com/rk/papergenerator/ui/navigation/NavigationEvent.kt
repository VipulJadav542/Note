package com.rk.papergenerator.ui.navigation

data class NavigationEvent(
    val command: NavigationCommand,
    val id: Long = System.currentTimeMillis()
)