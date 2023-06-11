package com.erkindilekci.moviebook.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val FontColor = Color(0xFF212121)
val SecondaryFontColor = Color(0xFF757575)
val DefaultBackgroundColor = Color(0xFFFAFAFA)
val LinkColor = Color(0xff64B5F6)

val TopAppBarColor = Color(0xFFf1191c)

val ColorScheme.inActiveChipColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF434343) else Color(0xFFfb423e)

val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White.copy(0.8f) else Color.White

val ColorScheme.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else TopAppBarColor

val ColorScheme.statusBarColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Color(0xFFdf0015)

val ColorScheme.cardColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF000000) else Color(0xFFFFFFFF)

val ColorScheme.cardContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray