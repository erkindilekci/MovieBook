package com.erkindilekci.moviebook.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.erkindilekci.moviebook.data.datasource.remote.ApiService
import com.erkindilekci.moviebook.data.datasource.remote.paging.GenrePagingDataSource
import com.erkindilekci.moviebook.data.datasource.remote.paging.NowPlayingPagingDataSource
import com.erkindilekci.moviebook.data.datasource.remote.paging.PopularPagingDataSource
import com.erkindilekci.moviebook.data.datasource.remote.paging.TopRatedPagingDataSource
import com.erkindilekci.moviebook.data.datasource.remote.paging.UpcomingPagingDataSource
import com.erkindilekci.moviebook.data.model.BaseModel
import com.erkindilekci.moviebook.data.model.Genres
import com.erkindilekci.moviebook.data.model.artist.Artist
import com.erkindilekci.moviebook.data.model.artist.ArtistDetail
import com.erkindilekci.moviebook.data.model.moviedetail.MovieDetail
import com.erkindilekci.moviebook.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.movieDetail(movieId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.recommendedMovie(movieId, page)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.search(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun genreList(): Flow<DataState<Genres>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.genreList()
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>> = flow {
        emit(DataState.Loading)
        try {
            val artistResult = apiService.movieCredit(movieId)
            emit(DataState.Success(artistResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> = flow {
        emit(DataState.Loading)
        try {
            val artistDetailResult = apiService.artistDetail(personId)
            emit(DataState.Success(artistDetailResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun nowPlayingPagingDataSource(genreId: String?) = Pager(
        pagingSourceFactory = { NowPlayingPagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun popularPagingDataSource(genreId: String?) = Pager(
        pagingSourceFactory = { PopularPagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun topRatedPagingDataSource(genreId: String?) = Pager(
        pagingSourceFactory = { TopRatedPagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun upcomingPagingDataSource(genreId: String?) = Pager(
        pagingSourceFactory = { UpcomingPagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

    fun genrePagingDataSource(genreId: String) = Pager(
        pagingSourceFactory = { GenrePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow
}
