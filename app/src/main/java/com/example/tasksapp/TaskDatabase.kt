package com.example.tasksapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase(){

    //define a property for taskDao
    abstract val taskDao: TaskDao

    //define a comapnion object so that we can call the getInstance()
    //method using TaskDatabse.getInstance()

    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).build()
                    INSTANCE = instance

                }
                return instance
            }
        }

    }
}