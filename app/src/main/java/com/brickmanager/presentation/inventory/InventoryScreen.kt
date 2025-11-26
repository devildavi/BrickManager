package com.brickmanager.presentation.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brickmanager.domain.entity.Set // La entidad de dominio se usa para mostrar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
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
    var showAddDialog by remember { mutableStateOf(false) } // State for the dialog

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mi Inventario LEGO") }) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
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

                // Show the Add Set Dialog when requested
                if (showAddDialog) {
                    AddSetDialog(
                        onDismiss = { showAddDialog = false },
                        onAddSet = { setId ->
                            viewModel.onAddSetClicked(setId)
                            showAddDialog = false
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }, // Show the dialog on click
                modifier = Modifier.padding(16.dp)
            ) {
                Text("+")
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
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = set.imageUrl,
                contentDescription = "Image of ${set.name}",
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${set.id} - ${set.name}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Pieces: ${set.pieceCount}", style = MaterialTheme.typography.bodySmall)
                Text(text = if (set.isBuilt) "✅ Built" else "📦 In box", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

/**
 * A dialog Composable to capture the ID of a new set to add.
 * @param onDismiss Callback invoked when the dialog is dismissed.
 * @param onAddSet Callback invoked when the user confirms adding the set, providing the ID.
 */
@Composable
fun AddSetDialog(
    onDismiss: () -> Unit,
    onAddSet: (String) -> Unit
) {
    var setIdInput by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Set by ID") },
        text = {
            OutlinedTextField(
                value = setIdInput,
                onValueChange = { setIdInput = it },
                label = { Text("Set ID (e.g., 75301)") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onAddSet(setIdInput) },
                enabled = setIdInput.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// --- Previews ---

@Preview(showBackground = true, name = "Inventory Item")
@Composable
fun InventorySetItemPreview() {
    BrickManagerTheme {
        InventorySetItem(
            set = Set(
                id = "75301-1",
                name = "Luke Skywalker's X-Wing Fighter",
                series = "Star Wars",
                pieceCount = 474,
                isBuilt = true,
                imageUrl = "https://cdn.rebrickable.com/media/sets/75301-1/58950.jpg"
            )
        )
    }
}

@Preview(showBackground = true, name = "Set List")
@Composable
fun SetListPreview() {
    val sampleSets = listOf(
        Set(id = "75301-1", name = "X-Wing", series = "Star Wars", pieceCount = 474, isBuilt = true, imageUrl = "https://cdn.rebrickable.com/media/sets/75301-1/58950.jpg"),
        Set(id = "10294-1", name = "Titanic", series = "Creator Expert", pieceCount = 9090, isBuilt = false, imageUrl = "https://cdn.rebrickable.com/media/sets/10294-1/37198.jpg")
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
        Set(id = "75301-1", name = "X-Wing", series = "Star Wars", pieceCount = 474, isBuilt = true, imageUrl = "https://cdn.rebrickable.com/media/sets/75301-1/58950.jpg"),
        Set(id = "10294-1", name = "Titanic", series = "Creator Expert", pieceCount = 9090, isBuilt = false, imageUrl = "https://cdn.rebrickable.com/media/sets/10294-1/37198.jpg")
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

@Preview(showBackground = true, name = "Add Set Dialog")
@Composable
fun AddSetDialogPreview() {
    BrickManagerTheme {
        AddSetDialog(
            onDismiss = {},
            onAddSet = {}
        )
    }
}
