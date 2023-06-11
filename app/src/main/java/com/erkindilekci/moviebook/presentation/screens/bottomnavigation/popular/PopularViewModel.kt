package com.erkindilekci.moviebook.presentation.screens.bottomnavigation.popular


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.erkindilekci.moviebook.data.model.GenreId
import com.erkindilekci.moviebook.data.model.moviedetail.Genre
import com.erkindilekci.moviebook.data.repository.MovieRepository
import com.erkindilekci.moviebook.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(val repo: MovieRepository) : ViewModel() {
    var selectedGenre: MutableState<Genre> =
        mutableStateOf(Genre(null, Constant.DEFAULT_GENRE_ITEM))
    val filterData = MutableStateFlow<GenreId?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val popularMovies = filterData.flatMapLatest {
        repo.popularPagingDataSource(it?.genreId)
    }.cachedIn(viewModelScope)
}