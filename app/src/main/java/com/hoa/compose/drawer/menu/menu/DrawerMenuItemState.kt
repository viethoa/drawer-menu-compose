package com.hoa.compose.drawer.menu.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow

@Composable
fun rememberDrawerMenuItemState(defaultItem: MenuItemType? = null): DrawerMenuItemState =
    rememberSaveable(saver = DrawerMenuItemState.Saver) {
        DrawerMenuItemState(defaultItem)
    }

class DrawerMenuItemState(id: MenuItemType? = null) {
    var selectedItem by mutableStateOf(id)
        private set

    fun set(type: MenuItemType) {
        selectedItem = type
    }

    fun asFlow(): Flow<MenuItemType?> = snapshotFlow { selectedItem }

    companion object {
        val Saver: Saver<DrawerMenuItemState, *> = Saver(
            save = { it.selectedItem?.value() },
            restore = { DrawerMenuItemState(it.toMenuItemType()) }
        )
    }
}