package com.hoa.compose.drawer.menu.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.hoa.compose.drawer.menu.menu.DrawerMenuType.Open.toDrawerMenuType
import kotlinx.coroutines.flow.Flow

@Composable
fun rememberDrawerMenuState(default: DrawerMenuType = DrawerMenuType.Closed) =
    rememberSaveable(default, saver = DrawerMenuState.Saver) {
        DrawerMenuState(default)
    }

class DrawerMenuState(default: DrawerMenuType = DrawerMenuType.Closed) {
    var value by mutableStateOf(default)
        private set

    fun set(newState: DrawerMenuType) {
        value = newState
    }

    fun toggle() {
        value = when (value) {
            is DrawerMenuType.Open -> DrawerMenuType.Closed
            is DrawerMenuType.Closed -> DrawerMenuType.Open
        }
    }

    fun asFlow(): Flow<DrawerMenuType> = snapshotFlow { value }

    companion object {
        val Saver: Saver<DrawerMenuState, *> = Saver(
            save = { it.value.asString() },
            restore = { DrawerMenuState(it.toDrawerMenuType()) }
        )
    }
}