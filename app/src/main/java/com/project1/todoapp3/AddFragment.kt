package com.project1.todoapp3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project1.todoapp3.databinding.FragmentAddBinding
import java.util.UUID

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: TaskViewModel by activityViewModels()
    private var currentTask: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Task>("task")?.let { task ->
            currentTask = task
            binding.todayTasks.setText(task.todayTasks)
            binding.weeklyTasks.setText(task.weeklyTasks)
            binding.monthlyTasks.setText(task.monthlyTasks)
        }

        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        binding.saveButton.setOnClickListener {
            onSaveClick()
        }
    }

    private fun onSaveClick() {
        val todayTasks = binding.todayTasks.text.toString()
        val weeklyTasks = binding.weeklyTasks.text.toString()
        val monthlyTasks = binding.monthlyTasks.text.toString()

        if (todayTasks.isNotBlank() && weeklyTasks.isNotBlank() && monthlyTasks.isNotBlank()) {
            val newTask = currentTask?.copy(
                todayTasks = todayTasks,
                weeklyTasks = weeklyTasks,
                monthlyTasks = monthlyTasks
            ) ?: Task(
                idTasks = UUID.randomUUID().toString(),
                todayTasks = todayTasks,
                weeklyTasks = weeklyTasks,
                monthlyTasks = monthlyTasks
            )

            if (currentTask != null) {
                viewModel.updateUser(newTask)
            } else {
                viewModel.insertUser(newTask)
            }
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = "Please fill in all fields"
        }
    }
}
