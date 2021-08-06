package com.mani.todolist


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private val listener: OnItemClickListener, private val context: Context) : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()) {


        class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val taskTV: TextView = itemView.findViewById(R.id.task_item_tv)
            val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_blank)
            val image: ImageView = itemView.findViewById(R.id.img_imp)

            fun bind(task: Task){
                taskTV.text = task.task
                checkBox.isChecked = task.completed
                taskTV.paint.isStrikeThruText = task.completed
                if(task.important){
                    image.setImageResource(R.drawable.ic_prioritise_flag)
                }else{
                    image.setImageResource(R.drawable.ic_unpriotiy_flag)
                }
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

            holder.image.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    listener.onImageViewClick(task, !(task.important))
                }
            }
            holder.taskTV.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    val intent = Intent(context, EditActivity::class.java)
                    intent.putExtra("extra", task.task)
                    context.startActivity(intent)
                }

            }

    }
}

interface OnItemClickListener {
    fun onCheckBoxClick(task: Task, isChecked: Boolean)
    fun onImageViewClick(task: Task, isChecked: Boolean)
}

    class TaskComparator : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task == newItem.task
        }

    }



