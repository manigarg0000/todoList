package com.mani.todolist


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val requestCodeForAddWordActivity = 1
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(task, isChecked)
    }

    override fun onImageViewClick(task: Task, isChecked: Boolean) {
        viewModel.onImageViewChanged(task, isChecked)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task, menu)
        val searchItem = menu?.findItem(R.id.search_icon)
        val searchView = searchItem?.actionView as SearchView


        searchView.onQueryTextChanged {
            (application as TaskApplication).repository.searchQuery.value = it
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.hide_complete -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedClick(item.isChecked)
                true
            }
            R.id.delete_complete -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Deletion confirmation")
                builder.setMessage("Are you sure you want to delete the completed tasks?")
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    viewModel.onDeleteCompleted()
                }

                builder.setNegativeButton("No") { dialogInterface, which ->
                    Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab_addNew)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = TaskListAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getAllWords.observe(this) { tasks ->
            tasks.let {
                adapter.submitList(it)

            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.currentList[viewHolder.adapterPosition]
                viewModel.onSwipe(task)
            }
        }).attachToRecyclerView(recyclerView)



        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNewTaskActivity::class.java)
            startActivityForResult(intent, requestCodeForAddWordActivity)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeForAddWordActivity && resultCode == RESULT_OK) {
            data?.getStringExtra(AddNewTaskActivity.EXTRA_REPLY)?.let {
                val task = Task(it)
                viewModel.insert(task)
            }
        } else {
            Toast.makeText(this, "Empty field cannot be added", Toast.LENGTH_LONG).show()
        }
    }
}