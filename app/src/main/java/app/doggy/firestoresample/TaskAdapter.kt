package app.doggy.firestoresample

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.doggy.firestoresample.databinding.TaskListItemBinding

class TaskAdapter: ListAdapter<Task, TaskViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("read_task", "onCreateViewHolder")
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d("read_task", "onBindViewHolder")
        holder.bind(getItem(position))
    }
}

class TaskViewHolder(
    private val binding: TaskListItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task) {
        binding.titleTextView.text = task.title
        binding.dateTextView.text = task.date
        Log.d("read_task", "bindできたお！")
    }
}

private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Task>() {
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
}