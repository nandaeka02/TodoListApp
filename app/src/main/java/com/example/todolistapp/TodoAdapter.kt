package com.example.todolistapp


import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.databinding.ItemTodoBinding

class TodoAdapter (
    var todos: List<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoAdapterViewHolder>()
{

    inner class TodoAdapterViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(view,parent,false)
        return TodoAdapterViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TodoAdapterViewHolder, position: Int) {
        val sharedPref = holder.itemView.context.getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val sharedTodo = holder.itemView.context.getSharedPreferences("myTodo",Context.MODE_PRIVATE)
        val editorSharedTodo = sharedTodo.edit()
        val editor = sharedPref.edit()
        val updateActivity = Intent(holder.itemView.context, UpdateTodoActivity::class.java)
        holder.binding.apply {
            tvIdItemTodo.text = todos[position].id
            tvTodo.text = todos[position].title
            cbTodo.isChecked = todos[position].isChecked
            tvTodo.setOnClickListener {
                editorSharedTodo.apply{
                    putString("data","${tvIdItemTodo.text.toString()}*${tvTodo.text.toString()}*${todos[position].isChecked}")
                    apply()
                }
                holder.itemView.context.startActivity(updateActivity)
            }
            if(cbTodo.isChecked){
                val spannableString1 = SpannableString(tvTodo.text)
                spannableString1.setSpan(StrikethroughSpan(),0,tvTodo.text.length,0)
                tvTodo.text = spannableString1
                todos[position].isChecked = true
            }
            cbTodo.setOnCheckedChangeListener{ _,isChecked ->
                if(cbTodo.isChecked){
                    val spannableString1 = SpannableString(tvTodo.text)
                    spannableString1.setSpan(StrikethroughSpan(),0,tvTodo.text.length,0)
                    tvTodo.text = spannableString1
                    todos[position].isChecked = true
                    editor.apply {
                        putString("${tvIdItemTodo.text.toString()}","${tvIdItemTodo.text.toString()}*${tvTodo.text.toString()}*true")
                        Log.d("cbTodo.isChecked","cbTodo.isChecked is true")
                        apply()
                    }
                }else{
                    tvTodo.text = tvTodo.text.toString()
                    todos[position].isChecked = false
                    editor.apply {
                        putString("${tvIdItemTodo.text.toString()}","${tvIdItemTodo.text.toString()}*${tvTodo.text.toString()}*false")
                        Log.d("cbTodo.isChecked","cbTodo.isChecked is false")
                        apply()
                    }
                }


            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}