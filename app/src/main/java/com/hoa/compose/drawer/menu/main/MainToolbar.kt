package com.hoa.compose.drawer.menu.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoa.compose.drawer.menu.R
import com.hoa.compose.drawer.menu.menu.DrawerMenuState

@Composable
fun MainToolbar(drawerMenuState: DrawerMenuState) {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    drawerMenuState.toggle()
                },
            painter = painterResource(R.drawable.ic_menu),
            contentDescription = "drawer-menu-icon"
        )
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            color = Color.Black,
            fontSize = 24.sp,
            text = "Drawer Menu"
        )
    }
}