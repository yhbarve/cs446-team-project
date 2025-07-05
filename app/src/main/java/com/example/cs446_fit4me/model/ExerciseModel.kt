
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


data class ExerciseTemplate(
    val id: String,
    val name: String,
    val muscleGroup: String,
    val bodyPart: String,
    val equipment: String,
    val isGeneral: Boolean,
    val imageURL: String?,
    val userId: String?,
    val createdAt: String
)

// if we want to convert, just keeping for now we can remove later
fun ExerciseTemplate.toExercise(): Exercise {
    return Exercise(
        name = name,
        muscleGroup = MuscleGroup.valueOf(muscleGroup),
        equipment = Equipment.valueOf(equipment),
        bodyPart = BodyPart.valueOf(bodyPart),
        description = "", // backend doesn’t send this
        isGeneric = isGeneral,
        imageUrl = imageURL
    )
}