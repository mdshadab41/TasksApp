package com.example.tasksapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tasksapp.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        val view = binding.root


        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        //create an EditTaskViewModelFactory
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao


        //Use a view model facotry to get a reference to view model
        val viewModelFactory = EditTaskViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditTaskViewModel::class.java)

        //set the layout data binding variable
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner//this interact with live data

        //Navigate to TaskFragment when navigateToList property
        //is set to true and called the viewmodel onNavigateToList() method

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigateToList()
            }
        })




        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}