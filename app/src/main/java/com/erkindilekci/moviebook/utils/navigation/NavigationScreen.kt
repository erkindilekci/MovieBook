package com.erkindilekci.moviebook.utils.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.erkindilekci.moviebook.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_title,
    val navIcon: (@Composable () -> Unit) = {
        Icon(
            Icons.Filled.Home, contentDescription = "home"
        )
    },
    val objectName: String = "",
    val objectPath: String = ""
) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Popular : Screen("popular_screen")
    object TopRated : Screen("top_rated_screen")
    object Upcoming : Screen("upcoming_screen")
    object NavigationDrawer :
        Screen("navigation_drawer", objectName = "genreId", objectPath = "/{genreId}")

    object MovieDetail :
        Screen("movie_detail_screen", objectName = "movieItem", objectPath = "/{movieItem}")

    object ArtistDetail :
        Screen("artist_detail_screen", objectName = "artistId", objectPath = "/{artistId}")

    object HomeNav : Screen("home_screen", title = R.string.home, navIcon = {
        Icon(
            Icons.Filled.Home,
            contentDescription = "search"
        )
    })

    object PopularNav : Screen("popular_screen", title = R.string.popular, navIcon = {
        Icon(
            Icons.Filled.Timeline,
            contentDescription = "search"
        )
    })

    object TopRatedNav : Screen("top_rated_screen", title = R.string.top_rate, navIcon = {
        Icon(
            Icons.Filled.Star,
            contentDescription = "search"
        )
    })

    object UpcomingNav : Screen("upcoming_screen", title = R.string.up_coming, navIcon = {
        Icon(
            Icons.Filled.Upcoming,
            contentDescription = "search"
        )
    })
}
