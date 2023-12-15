package com.example.tasksapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.TaskItemBinding
import com.example.tasksapp.generated.callback.OnClickListener

class TaskItemAdapter(val clickListener: (taskId: Long) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(
        TaskDiffItemCallback()
    ) {


    //this gets called when a view holder needs to be created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)

    //this gets called when data needs to be displayed in a viewholder
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class TaskItemViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {



        //Add an inflateFrom() method inside a companion object
        //this means that method can be called without first creating a TaskItemViewHolder object
        companion object {
            //this creates each viewholder  and inflate its layout
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)

                //the methods inflates the item's layout  and use it to create a TaskItemViewHolder

            }

        }

        fun bind(item: Task, clickListener: (taskId: Long) -> Unit) {
            binding.task = item
            binding.root.setOnClickListener { clickListener(item.taskId) }
        }

    }

}