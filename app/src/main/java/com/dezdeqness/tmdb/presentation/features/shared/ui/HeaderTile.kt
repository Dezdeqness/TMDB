package com.dezdeqness.tmdb.presentation.features.shared.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dezdeqness.tmdb.presentation.features.shared.model.HeaderUiModel

@Composable
fun HeaderTile(
    headerUiModel: HeaderUiModel,
    modifier: Modifier = Modifier,
) {
    Text(
        text = headerUiModel.date,
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W500,
            color = Color(0xDE000000),
            letterSpacing = 0.1.sp,
        ),
        modifier = modifier.fillMaxWidth(),
    )
}
