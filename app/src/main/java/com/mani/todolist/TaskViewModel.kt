package com.mani.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val getAllWords: LiveData<List<Task>> = repository.allWords.asLiveData()

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun onSwipe(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }


    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        repository.update(task.copy(completed = isChecked))
    }

    fun onImageViewChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        repository.update(task.copy(important = isChecked))
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        repository.hideCompleted.value = hideCompleted
    }

    fun onDeleteCompleted() = viewModelScope.launch {
        repository.deleteCompleted()
    }

    fun onTextChanged(text:String) = viewModelScope.launch {
        repository.insert(Task(text))
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