package com.erkindilekci.moviebook.presentation.screens.detailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erkindilekci.moviebook.R
import com.erkindilekci.moviebook.data.datasource.remote.ApiConstants
import com.erkindilekci.moviebook.data.model.BaseModel
import com.erkindilekci.moviebook.data.model.MovieItem
import com.erkindilekci.moviebook.data.model.artist.Artist
import com.erkindilekci.moviebook.data.model.artist.Cast
import com.erkindilekci.moviebook.data.model.moviedetail.MovieDetail
import com.erkindilekci.moviebook.utils.navigation.Screen
import com.erkindilekci.moviebook.presentation.component.CircularIndeterminateProgressBar
import com.erkindilekci.moviebook.presentation.component.ExpandingText
import com.erkindilekci.moviebook.presentation.theme.DefaultBackgroundColor
import com.erkindilekci.moviebook.presentation.theme.FontColor
import com.erkindilekci.moviebook.presentation.theme.statusBarColor
import com.erkindilekci.moviebook.presentation.theme.subTitlePrimary
import com.erkindilekci.moviebook.presentation.theme.subTitleSecondary
import com.erkindilekci.moviebook.utils.hourMinutes
import com.erkindilekci.moviebook.utils.network.DataState
import com.erkindilekci.moviebook.utils.pagingLoadingState
import com.erkindilekci.moviebook.utils.roundTo
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun DetailScreen(navController: NavController, movieId: Int) {
    val detailScreenViewModel = hiltViewModel<DetailScreenViewModel>()
    val progressBar = remember { mutableStateOf(false) }
    val movieDetail = detailScreenViewModel.movieDetail
    val recommendedMovie = detailScreenViewModel.recommendedMovie
    val artist = detailScreenViewModel.artist

    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.statusBarColor

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(statusBarColor)
    }

    LaunchedEffect(true) {
        detailScreenViewModel.movieDetailApi(movieId)
        detailScreenViewModel.recommendedMovieApi(movieId, 1)
        detailScreenViewModel.movieCredit(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            )
    ) {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)

        movieDetail.value?.let { it ->
            if (it is DataState.Success<MovieDetail>) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Box {
                        CoilImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f),
                            imageModel = { ApiConstants.IMAGE_URL.plus(it.data.poster_path) },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                contentDescription = "Movie detail",
                                colorFilter = null,
                            ),
                            component = rememberImageComponent {
                                +CircularRevealPlugin(
                                    duration = 800
                                )
                            }
                        )

                        IconButton(
                            modifier = Modifier.align(Alignment.TopStart),
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(
                            text = it.data.title,
                            modifier = Modifier.padding(top = 10.dp),
                            color = FontColor,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W500,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 7.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text(
                                    text = it.data.vote_average.roundTo(1).toString(),
                                    style = MaterialTheme.typography.subTitlePrimary,
                                    fontWeight = FontWeight.Medium
                                )

                                Text(
                                    text = stringResource(id = R.string.rating),
                                    style = MaterialTheme.typography.subTitleSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Column {
                                Text(
                                    text = it.data.runtime.hourMinutes(),
                                    style = MaterialTheme.typography.subTitlePrimary,
                                    fontWeight = FontWeight.Medium
                                )

                                Text(
                                    text = stringResource(id = R.string.duration),
                                    style = MaterialTheme.typography.subTitleSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Column {
                                Text(
                                    text = it.data.release_date,
                                    style = MaterialTheme.typography.subTitlePrimary,
                                    fontWeight = FontWeight.Medium
                                )

                                Text(
                                    text = stringResource(id = R.string.release_date),
                                    style = MaterialTheme.typography.subTitleSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.description),
                            color = FontColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        ExpandingText(text = it.data.overview)

                        recommendedMovie.value?.let {
                            if (it is DataState.Success<BaseModel>) {
                                RecommendedMovie(navController, it.data.results)
                            }
                        }
                        artist.value?.let {
                            if (it is DataState.Success<Artist>) {
                                ArtistAndCrew(navController, it.data.cast)
                            }
                        }
                    }
                }
            }
        }
        recommendedMovie.pagingLoadingState {
            progressBar.value = it
        }
        movieDetail.pagingLoadingState {
            progressBar.value = it
        }
    }
}

@Composable
fun RecommendedMovie(navController: NavController?, recommendedMovie: List<MovieItem>) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(R.string.similar),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(recommendedMovie, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    CoilImage(
                        modifier = Modifier
                            .height(180.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                navController?.navigate(
                                    Screen.MovieDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            },
                        imageModel = { ApiConstants.IMAGE_URL.plus(item.posterPath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "similar movie",
                            colorFilter = null,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                        },
                    )
                }
            })
        }
    }
}

@Composable
fun ArtistAndCrew(navController: NavController?, cast: List<Cast>) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(R.string.cast),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(cast, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 10.dp, top = 5.dp, bottom = 5.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoilImage(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .height(80.dp)
                            .width(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                navController?.navigate(
                                    Screen.ArtistDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            },
                        imageModel = { ApiConstants.IMAGE_URL.plus(item.profilePath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "artist and crew",
                            colorFilter = null,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                        },
                    )

                    Text(text = item.name, style = MaterialTheme.typography.subTitleSecondary)
                }
            })
        }
    }
}
