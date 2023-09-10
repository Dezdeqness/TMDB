package com.dezdeqness.tmdb.presentation.features.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dezdeqness.tmdb.R
import com.dezdeqness.tmdb.presentation.features.shared.action.Action
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import com.dezdeqness.tmdb.ui.theme.TMDBTheme
import com.dezdeqness.tmdb.ui.theme.TmdbTheme

@Composable
fun MovieTile(
    movieUiModel: MovieUiModel,
    onActionPreformed: (Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = cardElevation(3.dp),
        colors = cardColors(
            containerColor = TmdbTheme.colors.backgroundLight,
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
                        style = TmdbTheme.typography.headerTile.copy(color = TmdbTheme.colors.textPrimary)
                    )
                }

                Column(modifier = Modifier.padding(end = 8.dp)) {
                    Text(
                        text = movieUiModel.title,
                        style = TmdbTheme.typography.movieTileTitle.copy(color = TmdbTheme.colors.textPrimary),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Text(
                        text = movieUiModel.description,
                        style = TmdbTheme.typography.movieTileSubTitle.copy(color = TmdbTheme.colors.textSecondary),
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
                            if (movieUiModel.isFavourite) {
                                onActionPreformed(Action.RemoveFavourite(movieUiModel.id))
                            } else {
                                onActionPreformed(Action.AddFavourite(movieUiModel.id))
                            }
                        },
                    ) {

                        val resId =
                            if (movieUiModel.isFavourite) R.string.action_remove_title else R.string.action_like_title
                        Text(
                            text = stringResource(id = resId),
                            style = TmdbTheme.typography.buttonTitle.copy(color = TmdbTheme.colors.primaryClickable),
                        )
                    }

                    Box(modifier = Modifier.size(8.dp))

                    TextButton(
                        onClick = {
                            onActionPreformed(Action.Share(movieUiModel.id))
                        },
                    ) {

                        Text(
                            text = stringResource(id = R.string.action_share_title),
                            style = TmdbTheme.typography.buttonTitle.copy(color = TmdbTheme.colors.primaryClickable),
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
                isFavourite = true,
            ),
            onActionPreformed = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
