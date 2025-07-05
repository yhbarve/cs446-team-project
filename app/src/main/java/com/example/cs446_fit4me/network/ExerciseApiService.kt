package com.example.cs446_fit4me.network

import com.example.cs446_fit4me.model.ExerciseTemplate
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApiService {
    @GET("exercise-template/general")
    suspend fun getGeneralExercises(): List<ExerciseTemplate>

    @GET("exercise-template/by-user/{userId}")
    suspend fun getUserExercises(@Query("userId") userId: String): List<ExerciseTemplate>
}
