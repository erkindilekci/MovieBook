package com.erkindilekci.moviebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.erkindilekci.moviebook.presentation.screens.listscreen.ListScreen
import com.erkindilekci.moviebook.presentation.theme.MovieBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieBookTheme {
                ListScreen()
            }
        }
    }
}
