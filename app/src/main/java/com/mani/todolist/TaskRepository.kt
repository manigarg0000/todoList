package com.mani.todolist

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allWords : Flow<List<Task>> = taskDao.getAll()

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun insert(task: Task){
        taskDao.insert(task)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun delete(task: Task){
        taskDao.delete(task)
    }

}