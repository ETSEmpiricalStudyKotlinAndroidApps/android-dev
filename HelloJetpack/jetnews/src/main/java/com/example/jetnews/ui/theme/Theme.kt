package com.example.jetnews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val JNLightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = White,
    error = Red800,
    background = White,
    surface = White
)

private val JNDarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Black,
    secondary = Red300,
    onSecondary = Black,
    error = Red200,
    background = Black18,
    surface = Black18
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) JNDarkColors else JNLightColors,
        typography = JNTypography,
        shapes = JNShapes,
        content = content
    )
}
