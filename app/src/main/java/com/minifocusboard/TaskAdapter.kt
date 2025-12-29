package com.minifocusboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minifocusboard.databinding.ItemTaskBinding

class TaskAdapter(
    private val tasks: List<Task>,
    private val onToggleDone: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.tvTaskText.text = task.label
        
        // 先移除监听器，避免设置 isChecked 时触发回调
        holder.binding.cbTaskDone.setOnCheckedChangeListener(null)
        holder.binding.cbTaskDone.isChecked = task.done

        // Apply strikethrough style for completed tasks
        if (task.done) {
            holder.binding.tvTaskText.alpha = 0.6f
        } else {
            holder.binding.tvTaskText.alpha = 1.0f
        }

        // 设置完 isChecked 后再添加监听器
        holder.binding.cbTaskDone.setOnCheckedChangeListener { _, _ ->
            // 使用 holder.adapterPosition 确保获取正确的 position
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onToggleDone(currentPosition)
            }
        }

        holder.binding.btnDeleteTask.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onDelete(currentPosition)
            }
        }
    }

    override fun getItemCount() = tasks.size
}

