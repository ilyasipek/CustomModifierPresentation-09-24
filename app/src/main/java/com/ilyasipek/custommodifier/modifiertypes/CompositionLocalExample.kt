package com.ilyasipek.custommodifier.modifiertypes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class Theme(val backgroundColor: Color, )

val LightTheme = Theme(
    backgroundColor = Color.White,
)

val DarkTheme = Theme(
    backgroundColor = Color.Black,
)

// Define a CompositionLocal
val LocalAppTheme = staticCompositionLocalOf { LightTheme }

@Composable
fun MyApp(isDarkTheme: Boolean) {
    val theme = if (isDarkTheme) DarkTheme else LightTheme
    CompositionLocalProvider(LocalAppTheme provides theme) {
        // All child composables will have access to 'theme'
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    val currentTheme = LocalAppTheme.current
    println("Current BackgroundColor is: ${currentTheme.backgroundColor}")
}