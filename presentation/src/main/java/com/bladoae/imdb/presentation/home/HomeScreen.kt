package com.bladoae.imdb.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.domain.model.TopRated
import com.bladoae.imdb.presentation.R
import com.bladoae.imdb.presentation.theme.IMDbTheme

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = homeViewModel.topRatedMovies.observeAsState(Resource.Loading())
    HomeContent(
        uiState = uiState,
        onRetry = { homeViewModel.onRetry() }
    )
}

@Composable
private fun HomeContent(
    uiState: State<Resource<TopRated>>,
    onRetry: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when(uiState.value) {
            is Resource.Loading -> ShowLoading()
            is Resource.Success -> {
                Greeting("Android")
            }
            is Resource.Error -> {
                ShowError(onRetry)
            }
        }
    }
}

@Composable
private fun ShowLoading() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ShowError(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material.Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.error_message)
        )
        Button(
            onClick = { onRetry() },
            content = {
                androidx.compose.material.Text(
                    text = stringResource(id = R.string.retry),
                    style = androidx.compose.material.MaterialTheme.typography.body1,
                )
            }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IMDbTheme {
        Greeting("Android")
    }
}