package com.example.todolistapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityAddTodoBinding
import com.example.todolistapp.databinding.ActivityMainBinding

class AddTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddTodoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val sharedPref = getSharedPreferences( "myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        binding.btnNewTodo.setOnClickListener {
            if(!(binding.etNewTodo.text.isEmpty())){
                val name = binding.etNewTodo.text.toString()
                val isChecked = false
                editor.apply {
                    var numStr = ""
                    if(sharedPref.all.size < 10){
                        numStr = "0000${sharedPref.all.size}"
                    }else if(sharedPref.all.size < 100 && sharedPref.all.size >= 10){
                        numStr = "000${sharedPref.all.size}"
                    }else if(sharedPref.all.size < 1000 && sharedPref.all.size >= 100){
                        numStr = "00${sharedPref.all.size}"
                    }else if(sharedPref.all.size < 10000 && sharedPref.all.size >= 1000){
                        numStr = "0${sharedPref.all.size}"
                    }else if(sharedPref.all.size == 10000){
                        numStr = "${sharedPref.all.size}"
                    }

                    putString("$numStr", "$numStr*$name*$isChecked")

                    apply()
                }
                Toast.makeText(this,"Berhasil menambahkan list",Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

}