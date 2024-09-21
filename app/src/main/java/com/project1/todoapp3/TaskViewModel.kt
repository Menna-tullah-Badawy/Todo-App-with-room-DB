package com.project1.todoapp3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insertUser(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun updateUser(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun deleteUser(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }
}
