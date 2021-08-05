package com.mani.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "completed") val completed : Boolean = false
){
}