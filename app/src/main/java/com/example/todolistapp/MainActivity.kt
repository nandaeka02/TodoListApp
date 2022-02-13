package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var todolist = mutableListOf(
        Todo("00000","test",false)
    )
    val adapter = TodoAdapter(todolist)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val sharedPref = getSharedPreferences( "myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.tvGetPref.setText(sharedPref.all.toString())

        todolist.clear()
        if(!(sharedPref.all == null)){
            for(i in 0..sharedPref.all.size - 1){
                var numStr = ""
                if(i < 10){
                    numStr = "0000$i"
                }else if(i < 100 && i >= 10){
                    numStr = "000$i"
                }else if(i < 1000 && i >= 100){
                    numStr = "00$i"
                }else if(i < 10000 && i >= 1000){
                    numStr = "0$i"
                }else if(i == 10000){
                    numStr = "$i"
                }
                val data = sharedPref.getString("$numStr",null).toString().split("*")
                todolist.add(Todo(data[0],data[1],data[2].toBoolean()))
            }
        }

        binding.rvTodo.adapter = adapter
        binding.rvTodo.layoutManager = LinearLayoutManager(this)


        binding.btnNewTodo.setOnClickListener {
            Log.d("preff", "${sharedPref.all.toString()}")
            val secondActivity = Intent(this, AddTodoActivity::class.java)
            startActivity(secondActivity)
        }
        binding.btnDeleteAll.setOnClickListener {
            editor.apply {
                clear()
                apply()
            }
            todolist.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(this,"Berhasil menghapus semua list",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRestart() {
        Log.d("onresume", "On Resume")
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val sharedPref = getSharedPreferences( "myPref", Context.MODE_PRIVATE)
        todolist.clear()
        if(!(sharedPref.all == null)){
            for(i in 0..sharedPref.all.size - 1){
                var numStr = ""
                if(i < 10){
                    numStr = "0000$i"
                }else if(i < 100 && i >= 10){
                    numStr = "000$i"
                }else if(i < 1000 && i >= 100){
                    numStr = "00$i"
                }else if(i < 10000 && i >= 1000){
                    numStr = "0$i"
                }else if(i == 10000){
                    numStr = "$i"
                }
                val data = sharedPref.getString("$numStr",null).toString().split("*")
                todolist.add(Todo(data[0],data[1],data[2].toBoolean()))
            }
        }
        binding.rvTodo.adapter = adapter
        binding.rvTodo.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
        super.onRestart()
    }
}