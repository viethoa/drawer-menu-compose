package com.hoa.compose.drawer.menu.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoa.compose.drawer.menu.R
import com.hoa.compose.drawer.menu.ui.theme.DrawerMenuComposeTheme
import com.hoa.compose.drawer.menu.ui.theme.Pink80
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
internal fun MenuItem(
    name: String,
    type: MenuItemType,
    state: DrawerMenuItemState
) {
    println("MenuTiem ${type.value()}")
    var selected by remember { mutableStateOf(false) }
    LaunchedEffect(state) {
        state.asFlow()
            .map { it == type }
            .distinctUntilChanged()
            .filter { it != selected }
            .collect {
                selected = it
            }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 16.dp)
            .clip(shape = RoundedCornerShape(30.dp, 0.dp, 0.dp, 30.dp))
            .background(
                color = if (selected) Pink80 else Color.Transparent
            )
            .clickable {
                state.set(type)
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(type.toMenuIcon()),
            contentDescription = "menu-item-icon"
        )
        Text(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterVertically),
            color = Color.Black,
            fontSize = 20.sp,
            text = name
        )
    }
}

private fun MenuItemType.toMenuIcon(): Int {
    return when (this) {
        is MenuItemType.Home -> R.drawable.ic_home
        is MenuItemType.Sleep -> R.drawable.ic_sleep
        is MenuItemType.LeaderBoard -> R.drawable.ic_leaderboard
        is MenuItemType.Setting -> R.drawable.ic_setting
    }
}

@Preview(showBackground = true)
@Composable
internal fun MenuItemPreview() {
    DrawerMenuComposeTheme {
        val itemState = rememberDrawerMenuItemState(MenuItemType.Home)
        MenuItem("Home", MenuItemType.Home, itemState)
    }
}