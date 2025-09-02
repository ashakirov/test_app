package com.testtapyou.navigation

sealed class Route(val route: String) {
    object Main : Route("mainscreen")
    object Detail : Route("detail")
}