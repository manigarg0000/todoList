package com.mani.todolist

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class TaskRepository(private val taskDao: TaskDao) {

    val searchQuery = MutableStateFlow("")
    val hideCompleted = MutableStateFlow(false)

    private val taskFlow = combine(
        searchQuery,
        hideCompleted
    ){
        query, hideCompleted -> Pair(query, hideCompleted)

    }
        .flatMapLatest {
        taskDao.getAll(it.first, it.second)
    }

    val allWords : Flow<List<Task>> = taskFlow

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

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun update(task: Task){
        taskDao.update(task)
    }


}