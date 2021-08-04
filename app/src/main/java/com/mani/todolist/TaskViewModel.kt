package com.mani.todolist

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val getAllWords : LiveData<List<Task>> = repository.allWords.asLiveData()

    fun insert(word: Task) = viewModelScope.launch {
        repository.insert(word)
    }

    fun onSwipe(word: Task) = viewModelScope.launch {
        repository.delete(word)
    }

}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("unrecognised viewmodel class")
    }

}