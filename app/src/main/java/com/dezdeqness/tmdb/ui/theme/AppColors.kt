package com.dezdeqness.tmdb.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class AppColors(
    indicator: Color,
    textPrimary: Color,
    textSecondary: Color,
    primaryClickable: Color,
    tabSelected: Color,
    tabUnSelected: Color,
    background: Color,
    backgroundLight: Color,
    tabContainer: Color,
    appBar: Color,
) {
    var indicator by mutableStateOf(indicator)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var primaryClickable by mutableStateOf(primaryClickable)
        private set
    var tabSelected by mutableStateOf(tabSelected)
        private set
    var tabUnSelected by mutableStateOf(tabUnSelected)
        private set
    var background by mutableStateOf(background)
        private set
    var backgroundLight by mutableStateOf(backgroundLight)
        private set
    var tabContainer by mutableStateOf(tabContainer)
        private set
    var appBar by mutableStateOf(appBar)
        private set

    fun copy(
        indicator: Color = this.indicator,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        primaryClickable: Color = this.primaryClickable,
        tabSelected: Color = this.tabSelected,
        tabUnSelected: Color = this.tabUnSelected,
        background: Color = this.background,
        tabContainer: Color = this.tabContainer,
        appBar: Color = this.appBar,
        backgroundLight: Color = this.backgroundLight,
    ): AppColors = AppColors(
        indicator = indicator,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        primaryClickable = primaryClickable,
        tabSelected = tabSelected,
        tabUnSelected = tabUnSelected,
        background = background,
        appBar = appBar,
        tabContainer = tabContainer,
        backgroundLight = backgroundLight,
    )

    fun updateColorsFrom(other: AppColors) {
        indicator = other.indicator
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        primaryClickable = other.primaryClickable
        tabSelected = other.tabSelected
        tabUnSelected = other.tabUnSelected
        background = other.background
        backgroundLight = other.backgroundLight
        appBar = other.appBar
        tabContainer = other.tabContainer
    }
}

fun colors(
    indicator: Color = green1,
    textPrimary: Color = black1,
    textSecondary: Color = black2,
    primaryClickable: Color = purple1,
    tabSelected: Color = white1,
    tabUnSelected: Color = green2,
    background: Color = white1,
    backgroundLight: Color = white2,
    tabContainer: Color = purple1,
    appBar: Color = purple1,
): AppColors = AppColors(
    indicator = indicator,
    background = background,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    primaryClickable = primaryClickable,
    tabSelected = tabSelected,
    tabUnSelected = tabUnSelected,
    tabContainer = tabContainer,
    backgroundLight = backgroundLight,
    appBar = appBar,
)

internal val LocalColors = staticCompositionLocalOf { colors() }
