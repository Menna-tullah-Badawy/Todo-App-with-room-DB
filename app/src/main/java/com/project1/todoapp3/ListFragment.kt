package com.project1.todoapp3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.project1.todoapp3.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.mylist.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter(
            emptyList(),
            onItemClick = { /* Handle item click if needed */ },
            onEditClick = { position -> onEditClick(position) },
            onDeleteClick = { position -> onDeleteClick(position) }
        )
        binding.mylist.adapter = userAdapter
        loadTasks()
    }

    private fun loadTasks() {
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            val diffCallback = UserDiffCallback(userAdapter.getUserList(), tasks)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            userAdapter.updateUserList(tasks)
            diffResult.dispatchUpdatesTo(userAdapter)
        }
    }

    private fun onDeleteClick(position: Int) {
        val task = userAdapter.getTaskAtPosition(position)
        viewModel.deleteUser(task)
    }

    private fun onEditClick(position: Int) {
        val task = userAdapter.getTaskAtPosition(position)
        val bundle = Bundle().apply {
            putParcelable("task", task)
        }
        findNavController().navigate(R.id.action_listFragment_to_addFragment, bundle)
    }
}
