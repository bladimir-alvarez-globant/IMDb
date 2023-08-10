package com.bladoae.imdb.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.presentation.common.ErrorMessage
import com.bladoae.imdb.presentation.common.LoadingMessage
import com.bladoae.imdb.presentation.home.components.ListMovie
import com.bladoae.imdb.presentation.theme.IMDbTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = homeViewModel.topRatedMovies.observeAsState(Resource.Loading())
    HomeContent(
        uiState = uiState,
        onRetry = { homeViewModel.onRetry() }
    )
}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
private fun HomeContent(
    uiState: State<Resource<TopRated>>,
    onRetry: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when(uiState.value) {
            is Resource.Loading -> LoadingMessage()
            is Resource.Success -> {
                Column {
                    uiState.value.data?.results?.let { items ->
                        ListMovie(items)
                    }
                }
            }
            is Resource.Error -> {
                ErrorMessage(onRetry)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IMDbTheme {

    }
}