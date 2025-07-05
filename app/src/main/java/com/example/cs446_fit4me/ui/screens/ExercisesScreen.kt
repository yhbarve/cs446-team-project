package com.example.cs446_fit4me.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.navigation.NavController
import com.example.cs446_fit4me.model.Exercise
import com.example.cs446_fit4me.model.BodyPart
import com.example.cs446_fit4me.model.Equipment
import com.example.cs446_fit4me.model.MuscleGroup
import com.example.cs446_fit4me.ui.components.ExerciseListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(navController: NavController? = null) {
    var searchText by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }

    var bodyPartDropdownExpanded by remember { mutableStateOf(false) }
    var selectedBodyParts by remember { mutableStateOf(setOf<BodyPart>()) }

    var equipmentDropdownExpanded by remember { mutableStateOf(false) }
    var selectedEquipments by remember { mutableStateOf(setOf<Equipment>()) }

    var filterButtonSize by remember { mutableStateOf(IntSize.Zero) }

    val mockExercises = listOf(
        Exercise("Push Up", MuscleGroup.CHEST, Equipment.NONE, BodyPart.CHEST, "", true, "https://wger.de/media/exercise-images/91/Crunches-1.png"),
        Exercise("Back Extension", MuscleGroup.BACK, Equipment.NONE, BodyPart.BACK, "", true, null),
        Exercise("Battle Ropes", MuscleGroup.CORE, Equipment.NONE, BodyPart.CORE, "", true, null),
        Exercise("Squat", MuscleGroup.LEGS, Equipment.NONE, BodyPart.LEGS, "", true, null),
        Exercise("Arnold Press", MuscleGroup.SHOULDERS, Equipment.DUMBBELL, BodyPart.SHOULDERS, "", true, null),
        Exercise("Bench Press (Barbell)", MuscleGroup.CHEST, Equipment.BARBELL, BodyPart.CHEST, "", true, null),
        Exercise("Dumbbell Curl", MuscleGroup.ARMS, Equipment.DUMBBELL, BodyPart.ARMS, "", true, null),
        Exercise("Leg Press", MuscleGroup.LEGS, Equipment.MACHINE, BodyPart.LEGS, "", true, null),
        Exercise("Plank", MuscleGroup.CORE, Equipment.NONE, BodyPart.CORE, "", true, null),
        Exercise("Tricep Dips", MuscleGroup.ARMS, Equipment.NONE, BodyPart.ARMS, "", true, null),
        Exercise("Lat Pulldown", MuscleGroup.BACK, Equipment.MACHINE, BodyPart.BACK, "", true, null),
        Exercise("Cable Row", MuscleGroup.BACK, Equipment.MACHINE, BodyPart.BACK, "", true, null),
        Exercise("Overhead Tricep Extension", MuscleGroup.ARMS, Equipment.DUMBBELL, BodyPart.ARMS, "", true, null),
        Exercise("Deadlift", MuscleGroup.BACK, Equipment.BARBELL, BodyPart.BACK, "", true, null),
        Exercise("Leg Curl", MuscleGroup.LEGS, Equipment.MACHINE, BodyPart.LEGS, "", true, null)
    ).sortedBy { it.name }

    val myExercises = emptyList<Exercise>()

    // Pick base list depending on selected tab
    val baseExercises = if (selectedTabIndex == 0) mockExercises else myExercises

    // Filter exercises by search, selected body parts, and selected equipment
    val filteredExercises = baseExercises.filter { exercise ->
        exercise.name.startsWith(searchText, ignoreCase = true) &&
                (selectedBodyParts.isEmpty() || exercise.bodyPart in selectedBodyParts) &&
                (selectedEquipments.isEmpty() || exercise.equipment in selectedEquipments)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("Exercises") },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                singleLine = true,
                shape = MaterialTheme.shapes.small
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Body Part Filter Button and Dropdown
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .onGloballyPositioned { coordinates: LayoutCoordinates ->
                            filterButtonSize = coordinates.size
                        }
                ) {
                    FilterButton(
                        text = if (selectedBodyParts.isEmpty()) "Body Part" else "${selectedBodyParts.size} selected",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { bodyPartDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = bodyPartDropdownExpanded,
                        onDismissRequest = { bodyPartDropdownExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { (filterButtonSize.width * 0.8f).toDp() })
                            .heightIn(max = 300.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        BodyPart.values().forEach { bodyPart ->
                            val isSelected = selectedBodyParts.contains(bodyPart)
                            DropdownMenuItem(
                                onClick = {
                                    selectedBodyParts = if (isSelected) {
                                        selectedBodyParts - bodyPart
                                    } else {
                                        selectedBodyParts + bodyPart
                                    }
                                },
                                text = {
                                    Text(
                                        bodyPart.name.replaceFirstChar { it.uppercase() },
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else LocalContentColor.current
                                    )
                                },
                                modifier = Modifier.background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }

                // Equipment Filter Button and Dropdown
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .onGloballyPositioned { coordinates: LayoutCoordinates ->
                            // Optional: Track size separately if needed
                        }
                ) {
                    FilterButton(
                        text = if (selectedEquipments.isEmpty()) "Equipment" else "${selectedEquipments.size} selected",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { equipmentDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = equipmentDropdownExpanded,
                        onDismissRequest = { equipmentDropdownExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { (filterButtonSize.width * 0.8f).toDp() })
                            .heightIn(max = 300.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Equipment.values().forEach { equipment ->
                            val isSelected = selectedEquipments.contains(equipment)
                            DropdownMenuItem(
                                onClick = {
                                    selectedEquipments = if (isSelected) {
                                        selectedEquipments - equipment
                                    } else {
                                        selectedEquipments + equipment
                                    }
                                },
                                text = {
                                    Text(
                                        equipment.name.replaceFirstChar { it.uppercase() },
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else LocalContentColor.current
                                    )
                                },
                                modifier = Modifier.background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }
            }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Preset Exercises") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("My Exercises") }
                )
            }

            ExercisesList(
                exercises = filteredExercises,
                modifier = Modifier.weight(1f)
            )
        }

        FloatingActionButton(
            onClick = {
                // TODO: Implement add exercise logic later
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Exercise")
        }
    }
}

@Composable
fun ExercisesList(exercises: List<Exercise>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(exercises) { index, exercise ->
            ExerciseListItem(exercise = exercise)
            if (index < exercises.size - 1) {
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun FilterButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Surface(
        modifier = modifier
            .height(36.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 4.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = text)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Filled.FilterList, contentDescription = "Filter Icon")
            }
        }
    }
}
