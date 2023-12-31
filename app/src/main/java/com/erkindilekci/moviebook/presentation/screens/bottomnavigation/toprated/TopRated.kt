package com.erkindilekci.moviebook.presentation.screens.bottomnavigation.toprated

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erkindilekci.moviebook.data.model.GenreId
import com.erkindilekci.moviebook.data.model.moviedetail.Genre
import com.erkindilekci.moviebook.presentation.component.MovieItemList

@Composable
fun TopRated(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val topRatedViewModel = hiltViewModel<TopRatedViewModel>()
    MovieItemList(
        navController = navController,
        movies = topRatedViewModel.topRatedMovies,
        genres = genres,
        selectedName = topRatedViewModel.selectedGenre.value
    ) {
        topRatedViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            topRatedViewModel.selectedGenre.value = it
        }
    }
}