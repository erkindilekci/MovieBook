package com.erkindilekci.moviebook.presentation.screens.listscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.moviebook.R
import com.erkindilekci.moviebook.data.model.Genres
import com.erkindilekci.moviebook.data.model.moviedetail.Genre
import com.erkindilekci.moviebook.utils.navigation.Navigation
import com.erkindilekci.moviebook.utils.navigation.Screen
import com.erkindilekci.moviebook.utils.navigation.currentRoute
import com.erkindilekci.moviebook.presentation.component.CircularIndeterminateProgressBar
import com.erkindilekci.moviebook.presentation.component.SearchUI
import com.erkindilekci.moviebook.presentation.component.appbar.HomeAppBar
import com.erkindilekci.moviebook.presentation.component.appbar.SearchBar
import com.erkindilekci.moviebook.presentation.theme.statusBarColor
import com.erkindilekci.moviebook.presentation.theme.topAppBarBackgroundColor
import com.erkindilekci.moviebook.utils.Constant
import com.erkindilekci.moviebook.utils.network.DataState
import com.erkindilekci.moviebook.utils.networkconnection.ConnectionState
import com.erkindilekci.moviebook.utils.networkconnection.connectivityState
import com.erkindilekci.moviebook.utils.pagingLoadingState
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun ListScreen() {
    val listScreenViewModel = hiltViewModel<ListScreenViewModel>()
    val navController = rememberNavController()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreList = remember { mutableStateOf(arrayListOf<Genre>()) }
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.statusBarColor

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(statusBarColor)
    }

    LaunchedEffect(key1 = 0) {
        listScreenViewModel.genreList()
    }

    if (listScreenViewModel.genres.value is DataState.Success<Genres>) {
        genreList.value =
            (listScreenViewModel.genres.value as DataState.Success<Genres>).data.genres as ArrayList

        if (genreList.value.first().name != Constant.DEFAULT_GENRE_ITEM)
            genreList.value.add(0, Genre(null, Constant.DEFAULT_GENRE_ITEM))
    }

    Scaffold(
        topBar = {
            when (currentRoute(navController)) {
                Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route, Screen.NavigationDrawer.route -> {
                    if (isAppBarVisible.value) {
                        HomeAppBar(openSearchBar = { isAppBarVisible.value = false })
                    } else {
                        SearchBar(isAppBarVisible, listScreenViewModel)
                    }
                }
            }
        },
        bottomBar = {
            when (currentRoute(navController)) {
                Screen.Home.route, Screen.Popular.route, Screen.TopRated.route, Screen.Upcoming.route -> {
                    BottomNavigationUI(navController)
                }
            }
        }, snackbarHost = {
            if (isConnected.not()) {
                Snackbar(modifier = Modifier.padding(8.dp)) {
                    Text(text = stringResource(R.string.there_is_no_internet))
                }
            }
        }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it), genreList.value)
            Column {

                CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
                if (isAppBarVisible.value.not()) {
                    SearchUI(navController, listScreenViewModel.searchData) {
                        isAppBarVisible.value = true
                    }
                }
            }
        }
        listScreenViewModel.searchData.pagingLoadingState {
            searchProgressBar.value = it
        }
    }
}

@Composable
fun BottomNavigationUI(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
    ) {
        val items =
            listOf(Screen.HomeNav, Screen.PopularNav, Screen.TopRatedNav, Screen.UpcomingNav)

        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                icon = item.navIcon,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.topAppBarBackgroundColor.copy(0.5f),
                    selectedTextColor = Color.White,
                    selectedIconColor = Color.White,
                    unselectedTextColor = Color.White.copy(0.4f),
                    unselectedIconColor = Color.White.copy(0.4f)
                ),
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}
