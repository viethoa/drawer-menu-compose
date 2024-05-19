package com.hoa.compose.drawer.menu.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.hoa.compose.drawer.menu.menu.DrawerMenuItemState
import com.hoa.compose.drawer.menu.menu.DrawerMenuState
import com.hoa.compose.drawer.menu.ui.theme.PurpleGrey80
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
internal fun MainScreenContents(
    modifier: Modifier,
    drawerMenuState: DrawerMenuState,
    drawerMenuItemState: DrawerMenuItemState
) {
    var selectedMenu by remember { mutableStateOf("Home") }
    LaunchedEffect(drawerMenuItemState) {
        drawerMenuItemState.asFlow()
            .filter { it != null }
            .map { it.toString() }
            .collect {
                selectedMenu = it
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = PurpleGrey80)
    ) {
        MainToolbar(drawerMenuState)
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = selectedMenu,
            fontSize = 32.sp
        )
    }
}
