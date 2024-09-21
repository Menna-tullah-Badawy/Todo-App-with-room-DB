package com.project1.todoapp3

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val idTasks: String,
    val todayTasks: String,
    val weeklyTasks: String,
    val monthlyTasks: String
) : Parcelable
