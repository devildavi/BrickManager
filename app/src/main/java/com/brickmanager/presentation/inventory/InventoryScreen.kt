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
                        SetList(
                            sets = uiState.inventorySets,
                            onStatusChange = viewModel::onSetStatusChanged
                        )
                    }

                    else -> {
                        Text(
                            text = "You don\'t have any sets in your inventory yet. Add one!",
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
 * @param onStatusChange Callback invoked when the built status of a set changes.
 */
@Composable
fun SetList(sets: List<Set>, onStatusChange: (String, Boolean) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
        items(sets, key = { it.id }) { set ->
            InventorySetItem(
                set = set,
                onStatusChange = { newStatus ->
                    onStatusChange(set.id, newStatus)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * A Composable that displays a single inventory set item in a Card.
 * @param set The [Set] to display.
 * @param onStatusChange Callback invoked when the built status is toggled.
 */
@Composable
fun InventorySetItem(set: Set, onStatusChange: (Boolean) -> Unit) {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${set.id} - ${set.name}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Theme: ${set.series}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Pieces: ${set.pieceCount}", style = MaterialTheme.typography.bodySmall)
                set.minifigCount?.let {
                    Text(text = "Minifigures: $it", style = MaterialTheme.typography.bodySmall)
                }
                set.acquisitionDate?.let {
                    Text(text = "Acquired: $it", style = MaterialTheme.typography.bodySmall)
                }
                set.buildDate?.let {
                    Text(text = "Built: $it", style = MaterialTheme.typography.bodySmall)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = if (set.isBuilt) "✅ Built" else "📦 In box", style = MaterialTheme.typography.bodySmall)
                Switch(
                    checked = set.isBuilt,
                    onCheckedChange = onStatusChange
                )
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
                name = "Luke Skywalker\'s X-Wing Fighter",
                series = "Star Wars",
                pieceCount = 474,
                minifigCount = 4,
                isBuilt = true,
                imageUrl = "https://cdn.rebrickable.com/media/sets/75301-1/58950.jpg",
                acquisitionDate = "2023-10-27",
                buildDate = "2023-10-28"
            ),
            onStatusChange = {}
        )
    }
}

@Preview(showBackground = true, name = "Set List")
@Composable
fun SetListPreview() {
    val sampleSets = listOf(
        Set(id = "75301-1", name = "X-Wing", series = "Star Wars", pieceCount = 474, minifigCount = 4, isBuilt = true, imageUrl = "https://cdn.rebrickable.com/media/sets/75301-1/58950.jpg", acquisitionDate = "2023-10-27", buildDate = "2023-10-28"),
        Set(id = "10294-1", name = "Titanic", series = "Creator Expert", pieceCount = 9090, minifigCount = 0, isBuilt = false, imageUrl = "https://cdn.rebrickable.com/media/sets/10294-1/37198.jpg", acquisitionDate = "2023-10-26", buildDate = null)
    )
    BrickManagerTheme {
        SetList(sets = sampleSets, onStatusChange = { _, _ -> })
    }
}

// ... (rest of the previews remain the same)

