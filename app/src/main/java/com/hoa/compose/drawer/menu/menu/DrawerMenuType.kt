package com.hoa.compose.drawer.menu.menu

sealed class DrawerMenuType {
    data object Open : DrawerMenuType()
    data object Closed : DrawerMenuType()

    val isOpen: Boolean
        get() {
            return when (this) {
                is Open -> true
                else -> false
            }
        }

    fun asString(): String {
        return when (this) {
            is Open -> DRAWER_MENU_OPEN_VALUE
            is Closed -> DRAWER_MENU_CLOSED_VALUE
        }
    }

    fun String.toDrawerMenuType(): DrawerMenuType {
        return when (this) {
            DRAWER_MENU_OPEN_VALUE -> Open
            else -> Closed
        }
    }

    companion object {
        private const val DRAWER_MENU_OPEN_VALUE = "drawer-menu-open"
        private const val DRAWER_MENU_CLOSED_VALUE = "drawer-menu-closed"
    }
}