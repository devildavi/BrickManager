package com.example.brickmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brickmanager.presentation.inventory.InventoryScreen
import com.example.brickmanager.ui.theme.BrickManagerTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main entry point of the application.
 * This activity is annotated with [@AndroidEntryPoint] to enable Hilt for dependency injection.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrickManagerTheme {
                // Sets the InventoryScreen as the main content of the activity.
                InventoryScreen()
            }
        }
    }
}
