package com.dezdeqness.tmdb.presentation.features.shared.composer

import com.dezdeqness.tmdb.core.UiItem
import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import com.dezdeqness.tmdb.presentation.features.shared.model.HeaderUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.LoadMoreUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class UiModelComposer @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
) {
    private val formatter: SimpleDateFormat = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)

    fun composePage(
        items: List<MovieEntity>,
        hasNextPage: Boolean = false,
    ): List<UiItem> {

        val composeList = mutableListOf<UiItem>()

        var previousDate = ""

        items.forEach { item ->
            val date = formatter.format(item.releaseTimeStamp)
            if (previousDate.isEmpty() || date != previousDate) {
                composeList.add(
                    HeaderUiModel(
                        id = item.releaseTimeStamp,
                        date = date,
                    )
                )
                previousDate = date
            }
            composeList.add(
                MovieUiModel(
                    id = item.id,
                    title = item.title,
                    description = item.overview,
                    imageUrl = item.imageUrl,
                    score = item.voteAverage.toString(),
                    isFavourite = favouriteRepository.isAdded(item.id),
                ),
            )

        }

        if (hasNextPage) {
            composeList.add(LoadMoreUiModel)
        }

        return composeList
    }

}
