package com.example.todolistapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.todolistapp.databinding.ActivityUpdateTodoBinding

class UpdateTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUpdateTodoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val sharedTodo = getSharedPreferences("myTodo", Context.MODE_PRIVATE)
        val editorSharedTodo = sharedTodo.edit()
        val editor = sharedPref.edit()

        val data = sharedTodo.getString("data",null).toString().split("*")

        binding.etUpdateTodo.setText(data[1])
        binding.etUpdateTodo.setSelection(binding.etUpdateTodo.text.length)

        binding.btnUpdateTodo.setOnClickListener {

            if(!(binding.etUpdateTodo.text.isEmpty())){
                Log.d("printdata","$data")
                editor.apply{
                    putString("${data[0]}","${data[0]}*${binding.etUpdateTodo.text}*${data[2]}")
                    apply()
                }
                editorSharedTodo.clear()
                Toast.makeText(this,"Berhasil mengubah list", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}