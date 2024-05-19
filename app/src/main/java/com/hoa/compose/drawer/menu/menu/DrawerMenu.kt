package com.hoa.compose.drawer.menu.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hoa.compose.drawer.menu.main.MainToolbar
import com.hoa.compose.drawer.menu.ui.theme.DrawerMenuComposeTheme

@Composable
fun DrawerMenu(
    state: DrawerMenuItemState
) {
    println("DrawerMenu")
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            MenuItem("Home", MenuItemType.Home, state)
            MenuItem("Sleep", MenuItemType.Sleep, state)
            MenuItem("Leaderboard", MenuItemType.LeaderBoard, state)
            MenuItem("Settings", MenuItemType.Setting, state)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    DrawerMenuComposeTheme {
        DrawerMenu(rememberDrawerMenuItemState(MenuItemType.Home))
    }
}