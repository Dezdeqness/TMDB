package com.dezdeqness.tmdb.presentation.features.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import com.dezdeqness.tmdb.ui.theme.TMDBTheme
import java.util.Locale

@Composable
fun MovieTile(
    movieUiModel: MovieUiModel,
    onChangeFavouriteButtonClicked: (Long) -> Unit,
    onShareButtonClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = cardColors(
            containerColor = Color(0xFFFFFFFF),
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 18.dp)
                ) {
                    AsyncImage(
                        model = movieUiModel.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .size(40.dp),
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        text = movieUiModel.score,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Companion.Medium,
                            color = Color(0xDE000000),
                            letterSpacing = 0.1.sp,
                        )
                    )
                }

                Column(modifier = Modifier.padding(end = 8.dp)) {
                    Text(
                        text = movieUiModel.title,
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Companion.Medium,
                            color = Color(0xDE000000),
                            letterSpacing = 0.15.sp,
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Text(
                        text = movieUiModel.description,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Companion.Normal,
                            color = Color(0x99000000),
                            letterSpacing = 0.25.sp,
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                    )
                }
            }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {

                Row {
                    TextButton(
                        onClick = {
                            onChangeFavouriteButtonClicked(movieUiModel.id)
                        },
                    ) {
                        Text(
                            text = "Like".toUpperCase(Locale.getDefault()),
                            // Button
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight.Companion.Medium,
                                color = Color(0xFF6200EE),
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.25.sp,
                            ),
                        )
                    }

                    Box(modifier = Modifier.size(8.dp))

                    TextButton(
                        onClick = {
                            onShareButtonClicked(movieUiModel.id)
                        },
                    ) {

                        Text(
                            text = "Share".toUpperCase(Locale.getDefault()),
                            // Button
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight.Companion.Medium,
                                color = Color(0xFF6200EE),
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.25.sp,
                            )
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun MovieTilePreview() {
    TMDBTheme {
        MovieTile(
            movieUiModel = MovieUiModel(
                id = 1,
                title = "Pulp Fiction",
                description = "American neo-noir black comedy crime film written and directed by Quentin Tarantino",
                imageUrl = "https://image.tmdb.org/t/p/w780/8pjWz2lt29KyVGoq1mXYu6Br7dE.jpg",
                score = "7.5",
            ),
            onChangeFavouriteButtonClicked = {},
            onShareButtonClicked = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
