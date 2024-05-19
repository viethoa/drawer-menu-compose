package com.hoa.compose.drawer.menu.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hoa.compose.drawer.menu.menu.DrawerMenu
import com.hoa.compose.drawer.menu.menu.DrawerMenuType
import com.hoa.compose.drawer.menu.menu.MenuItemType
import com.hoa.compose.drawer.menu.menu.rememberDrawerMenuItemState
import com.hoa.compose.drawer.menu.menu.rememberDrawerMenuState
import com.hoa.compose.drawer.menu.ui.theme.DrawerMenuComposeTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun MainScreen(drawerWidth: Float) {
    val coroutineScope = rememberCoroutineScope()
    val drawerMenuItemState = rememberDrawerMenuItemState(MenuItemType.Home)
    val drawerMenuState = rememberDrawerMenuState(DrawerMenuType.Closed)
    val translationX = remember { Animatable(0f) }
    val decay = rememberSplineBasedDecay<Float>()
    var contentCorners by remember { mutableFloatStateOf(0f) }

    // The bounding for Open and Close menu: 0f -> DrawerWidth
    translationX.updateBounds(0f, drawerWidth)
    // Toggle DrawerMenu when state changed
    LaunchedEffect(drawerMenuState) {
        drawerMenuState.asFlow()
            .distinctUntilChanged()
            .collect {
                if (it.isOpen) {
                    translationX.animateTo(drawerWidth)
                } else {
                    translationX.animateTo(0f)
                }
            }
    }
    // Close drawer when DrawerMenuItem clicked
    LaunchedEffect(drawerMenuItemState) {
        drawerMenuItemState.asFlow()
            .distinctUntilChanged()
            .collect {
                drawerMenuState.set(DrawerMenuType.Closed)
            }
    }
    // Main Content Corner
    LaunchedEffect(translationX) {
        snapshotFlow { translationX.value }
            .map { if (it != 0f) 48f else 0f }
            .distinctUntilChanged()
            .collect {
                contentCorners = it
            }
    }
    // Move content without animation when dragging
    val draggableState = rememberDraggableState(onDelta = { dragAmount ->
        coroutineScope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    })

    DrawerMenu(drawerMenuItemState)
    MainScreenContents(
        Modifier
            .graphicsLayer {
                this.translationX = translationX.value
                val scale = lerp(1f.dp, 0.85f.dp, (translationX.value / drawerWidth))
                this.scaleX = scale.value
                this.scaleY = scale.value
                this.shape = RoundedCornerShape(contentCorners)
                this.clip = true
            }
            .draggable(
                draggableState,
                Orientation.Horizontal,
                onDragStopped = { velocity ->
                    val decayX = decay.calculateTargetValue(translationX.value, velocity)
                    val halfDrawerWidth = drawerWidth * 0.5
                    val targetX = if (decayX > halfDrawerWidth) drawerWidth else 0f
                    val canReachTargetWithDecay =
                        (decayX > targetX && targetX == drawerWidth) ||
                        (decayX < targetX && targetX == 0f)
                    coroutineScope.launch {
                        if (canReachTargetWithDecay) {
                            translationX.animateDecay(velocity, decay)
                        } else {
                            translationX.animateTo(targetX, initialVelocity = velocity)
                        }
                        drawerMenuState.set(
                            if (targetX == drawerWidth) {
                                DrawerMenuType.Open
                            } else {
                                DrawerMenuType.Closed
                            }
                        )
                    }
                }
            ),
        drawerMenuState,
        drawerMenuItemState
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DrawerMenuComposeTheme {
        MainScreen(1080f)
    }
}