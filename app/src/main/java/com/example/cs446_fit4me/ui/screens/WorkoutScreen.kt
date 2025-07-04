package com.example.cs446_fit4me.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Schedule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen() {
    Scaffold(
        floatingActionButton = { StartFAB() },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 80.dp, // extra for FAB
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                MyExercisesGrid()
            }

            item {
                StandardExercisesGrid()
            }
        }
    }
}

@Composable
fun StartFAB() {
    FloatingActionButton(
        onClick = { /* Start empty workout */ },
        containerColor = Color(0xFF007AFF)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Start Workout", tint = Color.White)
    }
}

@Composable
fun WorkoutCard(title: String, exercises: String, timeAgo: String? = null) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                exercises,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            timeAgo?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(it, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun MyExercisesGrid() {
    // REPLACE WITH ACTUAL DATA
    val myList = listOf(
        Triple("Choco & Aryaman", "Squat, Overhead Press, Deadlift", null),
    )
    val text = if (myList.size > 1) "My Exercises (${myList.size})" else "My Exercise (${myList.size})"

    Text(text, style = MaterialTheme.typography.titleMedium)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
    ) {
        items(myList) { (title, exercises, timeAgo) ->
            WorkoutCard(title, exercises, timeAgo)
        }
    }
}

@Composable
fun StandardExercisesGrid() {
    // REPLACE WITH ACTUAL DATA
    val templates = listOf(
        Triple("Strong 5×5 - Workout B", "Squat, Overhead Press, Deadlift", null),
        Triple("Legs", "Squat, Leg Extension, Flat Leg Raise", null),
        Triple("Strong 5×5 - Workout A", "Squat, Bench Press, Bent Over Row", null),
        Triple("Back and Biceps", "Deadlift, Seated Row, Lat Pulldown", null),
        Triple("Chest and Triceps", "Bench Press, Incline Press, Military Press", null)
    )
    val text = if (templates.size > 1) "Standard Exercises (${templates.size})" else "Standard Exercise (${templates.size})"
    Text(text, style = MaterialTheme.typography.titleMedium)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
    ) {
        items(templates) { (title, exercises, timeAgo) ->
            WorkoutCard(title, exercises, timeAgo)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutScreenPreview() {
    WorkoutScreen()
}
