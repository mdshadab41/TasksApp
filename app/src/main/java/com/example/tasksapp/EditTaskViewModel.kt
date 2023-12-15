package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditTaskViewModel(taskId: Long, val dao: TaskDao): ViewModel() {
    val task = dao.get(taskId)
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList : LiveData<Boolean>
        get() = _navigateToList


    //update the task
    fun updateTask(){
        viewModelScope.launch {
            dao.update(task.value!!)
            _navigateToList.value = true
        }
    }
    //delete the task
    fun deleteTask(){
        viewModelScope.launch {
            dao.delete(task.value!!)
            _navigateToList.value = true
        }
    }
    //add _navigateToList back to false
    fun onNavigateToList(){
        _navigateToList.value = false
    }

}