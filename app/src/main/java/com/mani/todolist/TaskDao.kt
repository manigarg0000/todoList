package com.mani.todolist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun deleteCompletedTasks()

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task_table WHERE(completed != :hideCompleted OR completed = 0) AND task LIKE '%' || :searchQuery || '%' ORDER BY important DESC")
    fun getAll(searchQuery:String, hideCompleted : Boolean) : Flow<List<Task>>

}