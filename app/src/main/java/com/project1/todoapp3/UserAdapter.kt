package com.project1.todoapp3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project1.todoapp3.databinding.ListItemBinding

class UserAdapter(
    private var userList: List<Task>,
    private val onItemClick: (Int) -> Unit,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    fun updateUserList(newList: List<Task>) {
        userList = newList
        notifyDataSetChanged()
    }

    fun getUserList(): List<Task> {
        return userList
    }

    fun getTaskAtPosition(position: Int): Task {
        return userList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = userList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.todayTasks.text = task.todayTasks
            binding.weeklyTasks.text = task.weeklyTasks
            binding.monthlyTasks.text = task.monthlyTasks
            binding.editButton.setOnClickListener {
                onEditClick(adapterPosition)
            }
            binding.deleteButton.setOnClickListener {
                onDeleteClick(adapterPosition)
            }
        }
    }
}
