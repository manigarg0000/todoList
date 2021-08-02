package com.mani.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class Task(
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "name") val completed : Boolean = false,
    @ColumnInfo(name = "name") val important : Boolean = false,
    @ColumnInfo(name = "name") val today : Boolean = false,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int = 0
) {
}