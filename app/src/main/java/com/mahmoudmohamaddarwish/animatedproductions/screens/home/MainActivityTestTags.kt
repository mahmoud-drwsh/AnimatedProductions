package com.mahmoudmohamaddarwish.animatedproductions.screens.home

/**
 * To save myself the trouble of having to create a unique tag for each composable, I created
 * an enum value for each of the composables that I want to use in the test, so that duplications
 * would be detected at runtime.
 * */
enum class MainActivityTestTags {
    ROOT_COMPOSABLE,
    NAV_BAR,
    MOVIES_NAV_ITEM,
    SHOWS_NAV_ITEM,
    FAVORITE_MOVIES_NAV_ITEM,
    FAVORITE_SHOWS_NAV_ITEM,
    MOVIES_LIST,
    SHOWS_LIST,
    FAVORITE_MOVIES_LIST,
    FAVORITE_SHOWS_LIST,
    POSTER_IMAGE,
    UI_MODE_ICON_BUTTON,
    MAIN_TOP_APP_BAR,
}