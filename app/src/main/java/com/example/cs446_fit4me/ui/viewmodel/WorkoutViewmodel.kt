package com.example.cs446_fit4me.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cs446_fit4me.model.WorkoutExerciseLinkModel
import com.example.cs446_fit4me.model.WorkoutModel

class WorkoutViewModel : ViewModel() {

    private val _myWorkouts = mutableStateListOf<WorkoutModel>()
    val myWorkouts: List<WorkoutModel> get() = _myWorkouts

    val standardWorkouts: List<WorkoutModel> = listOf(
        WorkoutModel("Strong 5×5 - Workout B", true, listOf(
            WorkoutExerciseLinkModel("Squat", orderInWorkout = 1),
            WorkoutExerciseLinkModel("Overhead Press", orderInWorkout = 2),
            WorkoutExerciseLinkModel("Deadlift", orderInWorkout = 3)
        )),
        WorkoutModel("Legs", true, listOf(
            WorkoutExerciseLinkModel("Squat", orderInWorkout = 1),
            WorkoutExerciseLinkModel("Leg Extension", orderInWorkout = 2),
            WorkoutExerciseLinkModel("Flat Leg Raise", orderInWorkout = 3)
        )),
        WorkoutModel("Chest and Triceps", true, listOf(
            WorkoutExerciseLinkModel("Bench Press", orderInWorkout = 1),
            WorkoutExerciseLinkModel("Incline Press", orderInWorkout = 2),
            WorkoutExerciseLinkModel("Military Press", orderInWorkout = 3)
        ))
    )

    fun addWorkout(workout: WorkoutModel) {
        _myWorkouts.add(workout)
    }

    fun addMockWorkout() {
        addWorkout(
            WorkoutModel(
                name = "New Custom Workout",
                isGeneric = false,
                exercises = listOf(
                    WorkoutExerciseLinkModel("Push Up", orderInWorkout = 1),
                    WorkoutExerciseLinkModel("Plank", orderInWorkout = 2)
                )
            )
        )
    }
    fun deleteWorkout(name: String) {
        _myWorkouts.removeIf { it.name == name } // TODO: Use unique ID in future maybe idk
    }

}
