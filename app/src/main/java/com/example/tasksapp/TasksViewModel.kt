package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

//pass a TaskDao object to TaskViewModel
class TasksViewModel(val dao: TaskDao): ViewModel(){
    var newTaskName = ""//this is for the taskname
    //get the record from database
    val task = dao.getAll()

    //add the navigate to Task property, along with
    //its backing property _navigateToTask
    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
        get() = _navigateToTask




    //This method creates a task object and uses the TaskDao insert()
    //method to add it to the database
    fun addTask(){
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }

    }

    fun onTaskClicked(taskId: Long){
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated(){
        _navigateToTask.value = null
    }


}