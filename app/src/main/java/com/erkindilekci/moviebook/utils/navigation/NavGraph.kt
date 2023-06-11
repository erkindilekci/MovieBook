package com.erkindilekci.moviebook.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.erkindilekci.moviebook.R
import com.erkindilekci.moviebook.data.model.moviedetail.Genre
import com.erkindilekci.moviebook.presentation.screens.artistdetail.ArtistDetail
import com.erkindilekci.moviebook.presentation.screens.bottomnavigation.nowplaying.NowPlaying
import com.erkindilekci.moviebook.presentation.screens.bottomnavigation.popular.Popular
import com.erkindilekci.moviebook.presentation.screens.bottomnavigation.toprated.TopRated
import com.erkindilekci.moviebook.presentation.screens.bottomnavigation.upcoming.Upcoming
import com.erkindilekci.moviebook.presentation.screens.detailscreen.DetailScreen

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier, genres: ArrayList<Genre>? = null,
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            NowPlaying(
                navController = navController,
                genres
            )
        }
        composable(Screen.Popular.route) {
            Popular(
                navController = navController,
                genres
            )
        }
        composable(Screen.TopRated.route) {
            TopRated(
                navController = navController,
                genres
            )
        }
        composable(Screen.Upcoming.route) {
            Upcoming(
                navController = navController,
                genres
            )
        }
        composable(
            Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
            arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
            movieId?.let {
                DetailScreen(
                    navController = navController, movieId
                )
            }
        }
        composable(
            Screen.ArtistDetail.route.plus(Screen.ArtistDetail.objectPath),
            arguments = listOf(navArgument(Screen.ArtistDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.artist_detail)
            val artistId = it.arguments?.getInt(Screen.ArtistDetail.objectName)
            artistId?.let {
                ArtistDetail(
                    artistId
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
