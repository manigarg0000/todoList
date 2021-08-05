package com.mani.todolist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private val listener: OnItemClickListener) : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()) {


        class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val taskTV: TextView = itemView.findViewById(R.id.task_item_tv)
            val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_blank)

            fun bind(task: Task){
                taskTV.text = task.task
                checkBox.isChecked = task.completed
                taskTV.paint.isStrikeThruText = task.completed
            }


            companion object{
                fun create(parent: ViewGroup) : TaskViewHolder{
                    val view : View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_view, parent, false)

                    return TaskViewHolder(view)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            return TaskViewHolder.create(parent)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current)
            holder.checkBox.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    listener.onCheckBoxClick(task, holder.checkBox.isChecked)
                }
            }
        }

    }

interface OnItemClickListener {
    //fun onItemClick(task: Task)
    fun onCheckBoxClick(task: Task, isChecked: Boolean)
}

    class TaskComparator : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task == newItem.task
        }

    }



