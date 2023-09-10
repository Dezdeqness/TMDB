package com.dezdeqness.tmdb.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object TmdbTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = AppTypography()

}

@Composable
fun TMDBTheme(
    colors: AppColors = TmdbTheme.colors,
    typography: AppTypography = TmdbTheme.typography,
    content: @Composable () -> Unit
) {

    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography,
    ) {
        content()
    }
}
