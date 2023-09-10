package com.dezdeqness.tmdb.presentation.features.shared.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dezdeqness.tmdb.presentation.features.shared.model.HeaderUiModel
import com.dezdeqness.tmdb.ui.theme.TmdbTheme

@Composable
fun HeaderTile(
    headerUiModel: HeaderUiModel,
    modifier: Modifier = Modifier,
) {
    Text(
        text = headerUiModel.date,
        style = TmdbTheme.typography.headerTile.copy(color = TmdbTheme.colors.textPrimary),
        modifier = modifier.fillMaxWidth(),
    )
}
