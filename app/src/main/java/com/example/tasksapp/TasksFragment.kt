package com.example.tasksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.databinding.FragmentTasksBinding


class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding?=null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        //build the database if it doesn;t already exist
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        //get the viewmodel
        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(TasksViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //create a taskitem adapter
        val adapter = TaskItemAdapter{taskId ->
            viewModel.onTaskClicked(taskId)
            
        }

        //Attach the adapter to the recyclerview
        binding.tasksList.adapter = adapter

        //this passes data to the adapter
        viewModel.task.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections
                    .actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        })

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}