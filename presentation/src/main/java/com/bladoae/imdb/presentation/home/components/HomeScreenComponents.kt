package com.bladoae.imdb.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bladoae.imdb.domain.model.Movie
import com.bladoae.imdb.presentation.R
import com.bladoae.imdb.presentation.theme.IMDbTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun SearchBox(
    onSearch: (value: String) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        // TextField placed within the Box
        val defaultText = stringResource(R.string.find_your_favorite_movie)
        BasicTextField(
            value = textState,
            maxLines = 1,
            onValueChange = { textState = it },
            textStyle = LocalTextStyle.current.copy(color = Color.LightGray),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.White, RoundedCornerShape(percent = 10))
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "searchIcon",
                        Modifier
                            .padding(2.dp, 0.dp)
                            .size(20.dp),
                        Color.LightGray
                    )
                    if (textState.text.isEmpty()) {
                        Text(
                            text = defaultText,
                            color = Color.LightGray
                        )
                    }
                    // <-- Add this
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch.invoke(textState.text) })
        )
    }
}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun ListMovie(
    items: List<Movie>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        items(items.size) { index ->
            ItemMovie(items[index])
        }
    }
}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun ItemMovie(
    movie: Movie
) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth().weight(1f)
            ) {
                GlideImage(
                    model = movie.posterPath,
                    contentDescription = "movieImage",
                    modifier = Modifier
                        .defaultMinSize(150.dp, 150.dp)
                        .fillMaxWidth(),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp),
            ) {
                Text(
                    text = movie.title?.replaceFirstChar { it.uppercase() } ?: "",
                    modifier = Modifier
                        .padding(10.dp, 5.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = movie.releaseDate ?: "",
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }
        }

    }
}

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun ItemMoviePreview() {
    IMDbTheme {
        val movie = Movie(
            title = "The Shawshank Redemption",
            releaseDate = "2019",
            overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
            posterPath = "https://cdn.britannica.com/60/182360-050-CD8878D6/Avengers-Age-of-Ultron-Joss-Whedon.jpg"
        )
        ItemMovie(movie)
    }
}