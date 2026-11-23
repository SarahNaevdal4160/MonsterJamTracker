package com.example.monsterjamtracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterJamScreen(
    viewModel: MonsterJamViewModel = viewModel()
) {
    val trucks = viewModel.trucks
    val seriesOptions = listOf("Series 34", "Series 35", "2-Pack", "Special")

    var nameInput by remember { mutableStateOf("") }
    var selectedSeries by remember { mutableStateOf(seriesOptions.first()) }
    var truckToEdit by remember { mutableStateOf<MonsterTruck?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monster Jam Samling") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Formulär för att lägga till bil
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Namn på Monster Truck (t.ex. Grave Digger)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Välj serie:", style = MaterialTheme.typography.labelMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                seriesOptions.forEach { series ->
                    FilterChip(
                        selected = selectedSeries == series,
                        onClick = { selectedSeries = series },
                        label = { Text(series) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.addTruck(nameInput, selectedSeries)
                    nameInput = ""
                },
                enabled = nameInput.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lägg till i listan")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Statistikrad
            val collectedCount = trucks.count { it.isCollected }
            Text(
                text = "Samlade: $collectedCount av ${trucks.size} bilar",
                style = MaterialTheme.typography.titleMedium
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Tomt läge eller rullande lista
            if (trucks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Inga Monster Trucks i listan ännu.\nLägg till en bil ovan för att börja hålla koll!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(trucks, key = { it.id }) { truck ->
                        MonsterTruckItem(
                            truck = truck,
                            onToggleCollected = { viewModel.toggleCollected(truck.id) },
                            onEditClick = { truckToEdit = truck },
                            onDeleteClick = { viewModel.deleteTruck(truck.id) }
                        )
                    }
                }
            }
        }

        // Redigeringsdialog
        truckToEdit?.let { truck ->
            EditTruckDialog(
                truck = truck,
                seriesOptions = seriesOptions,
                onDismiss = { truckToEdit = null },
                onSave = { newName, newSeries ->
                    viewModel.editTruck(truck.id, newName, newSeries)
                    truckToEdit = null
                }
            )
        }
    }
}

@Composable
fun MonsterTruckItem(
    truck: MonsterTruck,
    onToggleCollected: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = truck.isCollected,
                onCheckedChange = { onToggleCollected() }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = truck.name,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (truck.isCollected) TextDecoration.LineThrough else null
                )
                Text(
                    text = truck.series,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Redigera bil")
            }

            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Ta bort bil")
            }
        }
    }
}

@Composable
fun EditTruckDialog(
    truck: MonsterTruck,
    seriesOptions: List<String>,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var editedName by remember { mutableStateOf(truck.name) }
    var editedSeries by remember { mutableStateOf(truck.series) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Redigera Monster Truck") },
        text = {
            Column {
                OutlinedTextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    label = { Text("Namn") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Serie:", style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    seriesOptions.forEach { series ->
                        FilterChip(
                            selected = editedSeries == series,
                            onClick = { editedSeries = series },
                            label = { Text(series) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(editedName, editedSeries) },
                enabled = editedName.isNotBlank()
            ) {
                Text("Spara")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Avbryt")
            }
        }
    )
}