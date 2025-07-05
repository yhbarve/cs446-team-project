package com.example.cs446_fit4me.model

data class WorkoutModel(
    val name: String,
    val isGeneric: Boolean? = false,
    val exercises: List<WorkoutExerciseLinkModel>
)

