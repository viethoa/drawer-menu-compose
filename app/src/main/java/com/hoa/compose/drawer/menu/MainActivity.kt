package com.hoa.compose.drawer.menu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hoa.compose.drawer.menu.main.MainScreen
import com.hoa.compose.drawer.menu.ui.theme.DrawerMenuComposeTheme

/**
 * Following https://youtu.be/HNSKJIQtb4c?si=LMCOVTfj1QUWizfi
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = resources.displayMetrics
        val windowWidth = displayMetrics.widthPixels
        val drawerWidth = (windowWidth * 0.7).toFloat()
        setContent {
            DrawerMenuComposeTheme {
                MainScreen(drawerWidth)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    DrawerMenuComposeTheme {
        MainScreen(1080f)
    }
}