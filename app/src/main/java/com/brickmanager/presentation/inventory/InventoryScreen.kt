package com.brickmanager.presentation.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brickmanager.domain.entity.Set // La entidad de dominio se usa para mostrar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.tooling.preview.Preview
import com.example.brickmanager.ui.theme.BrickManagerTheme

/**
 * The main Composable for the Inventory screen.
 * It observes the [InventoryViewModel] and displays the UI according to the current [InventoryUiState].
 *
 * @param viewModel The [InventoryViewModel] instance, provided by Hilt.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mi Inventario LEGO") }) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    uiState.errorMessage != null -> {
                        Text(
                            text = uiState.errorMessage ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    uiState.inventorySets.isNotEmpty() -> {
                        SetList(sets = uiState.inventorySets)
                    }
                    else -> {
                        Text(
                            text = "You don't have any sets in your inventory yet. Add one!",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                FloatingActionButton(
                    onClick = {
                        // Example: This would trigger an event to add a set.
                        // viewModel.onAddSetClicked("75301") 
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text("+") // Or an Icon
                }
            }
        }
    )
}

/**
 * A Composable that displays a vertical list of LEGO sets.
 * @param sets The list of [Set] objects to display.
 */
@Composable
fun SetList(sets: List<Set>) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
        items(sets, key = { it.id }) { set ->
            InventorySetItem(set = set)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * A Composable that displays a single inventory set item in a Card.
 * @param set The [Set] to display.
 */
@Composable
fun InventorySetItem(set: Set) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${set.id} - ${set.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Pieces: ${set.pieceCount}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Estimated Value: $${set.estimatedMarketValue}", style = MaterialTheme.typography.bodySmall)
            Text(text = if (set.isBuilt) "✅ Built" else "📦 In box", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Inventory Item")
@Composable
fun InventorySetItemPreview() {
    BrickManagerTheme {
        InventorySetItem(
            set = Set(
                id = "75301",
                name = "Luke Skywalker's X-Wing Fighter",
                series = "Star Wars",
                pieceCount = 474,
                isBuilt = true,
                estimatedMarketValue = 49.99
            )
        )
    }
}

@Preview(showBackground = true, name = "Set List")
@Composable
fun SetListPreview() {
    val sampleSets = listOf(
        Set(id = "75301", name = "X-Wing", series = "Star Wars", pieceCount = 474, isBuilt = true, estimatedMarketValue = 49.99),
        Set(id = "10294", name = "Titanic", series = "Creator Expert", pieceCount = 9090, isBuilt = false, estimatedMarketValue = 679.99)
    )
    BrickManagerTheme {
        SetList(sets = sampleSets)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Screen - With Data")
@Composable
fun InventoryScreenWithDataPreview() {
    val sampleSets = listOf(
        Set(id = "75301", name = "X-Wing", series = "Star Wars", pieceCount = 474, isBuilt = true, estimatedMarketValue = 49.99),
        Set(id = "10294", name = "Titanic", series = "Creator Expert", pieceCount = 9090, isBuilt = false, estimatedMarketValue = 679.99)
    )
    BrickManagerTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Mi Inventario LEGO") }) },
            content = { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
                    SetList(sets = sampleSets)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Screen - Loading")
@Composable
fun InventoryScreenLoadingPreview() {
    BrickManagerTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Mi Inventario LEGO") }) },
            content = { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Screen - Empty")
@Composable
fun InventoryScreenEmptyPreview() {
    BrickManagerTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Mi Inventario LEGO") }) },
            content = { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text(text = "You don't have any sets in your inventory yet. Add one!")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Screen - Error")
@Composable
fun InventoryScreenErrorPreview() {
    BrickManagerTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Mi Inventario LEGO") }) },
            content = { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text(text = "Unknown error", color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}