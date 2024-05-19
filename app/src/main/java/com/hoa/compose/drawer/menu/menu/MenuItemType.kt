package com.hoa.compose.drawer.menu.menu

sealed class MenuItemType {
    data object Home : MenuItemType()
    data object Sleep : MenuItemType()
    data object LeaderBoard : MenuItemType()
    data object Setting : MenuItemType()
}

fun MenuItemType.value(): Int {
    return when (this) {
        is MenuItemType.Home -> 1
        is MenuItemType.Sleep -> 2
        is MenuItemType.LeaderBoard -> 3
        is MenuItemType.Setting -> 4
    }
}

fun Int.toMenuItemType(): MenuItemType? {
    return when (this) {
        1 -> MenuItemType.Home
        2 -> MenuItemType.Sleep
        3 -> MenuItemType.LeaderBoard
        4 -> MenuItemType.Setting
        else -> null
    }
}