
package com.example.cs446_fit4me.model

data class Exercise(
    val name: String,
    val muscleGroup: MuscleGroup,
    val equipment: Equipment,
    val bodyPart: BodyPart,
    val description: String,
    val isGeneric: Boolean,
    val imageUrl: String? = null
)

enum class MuscleGroup { CHEST, BACK, LEGS, ARMS, SHOULDERS, CORE }
enum class Equipment { NONE, DUMBBELL, BARBELL, MACHINE, BAND }
enum class BodyPart { CHEST, BACK, LEGS, ARMS, SHOULDERS, CORE }
