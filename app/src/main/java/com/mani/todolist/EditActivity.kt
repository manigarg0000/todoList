package com.mani.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity


class EditActivity : AppCompatActivity() {
    private lateinit var editText : EditText


    private val viewModel : TaskViewModel by viewModels{
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        editText = findViewById(R.id.editText)
        val prev = intent.getStringExtra("extra")
        editText.setText(prev)
        viewModel.onSwipe(Task(editText.text.toString()))
        val save = findViewById<Button>(R.id.save)
        save.setOnClickListener {
            viewModel.insert(Task(editText.text.toString()))
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("extraresult", editText.text.toString())
            startActivity(intent)
        }
    }
}