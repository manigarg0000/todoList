package com.mani.todolist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter() : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskComparator()) {

        class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val taskTV : TextView = itemView.findViewById(R.id.task_item_tv)
            fun bind(text : String?){
                taskTV.text = text
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
            holder.bind(current.task)

//            holder.taskTV.setOnClickListener {
//                val queryUrl: Uri = Uri.parse("${MainActivity.PREFIX_FORSEARCH}${current.word}")
//                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
//                context.startActivity(intent)
//            }
//
//

        }

    }

    class TaskComparator : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task == newItem.task
        }

    }



