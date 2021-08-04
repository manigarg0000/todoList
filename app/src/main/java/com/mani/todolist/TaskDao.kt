package com.mani.todolist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task_table WHERE task LIKE '%' || :searchQuery || '%' ")
    fun getAll(searchQuery:String) : Flow<List<Task>>

}