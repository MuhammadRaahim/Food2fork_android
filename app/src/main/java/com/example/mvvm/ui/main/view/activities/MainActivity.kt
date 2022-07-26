package com.example.mvvm.ui.main.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.model.Blog
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.ui.base.ViewModelFactory
import com.example.mvvm.ui.main.adapter.NamesAdapter
import com.example.mvvm.ui.main.callbacks.OnItemDeleteListener
import com.example.mvvm.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), OnItemDeleteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NamesAdapter
    private lateinit var nameList: ArrayList<Blog>
    private val viewModel by viewModels<MainViewModel>{ViewModelFactory()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setRecyclerView()
        setUpObserver()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.apply {
            btnSubmit.setOnClickListener{
                addData()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addData() {
        val name = binding.etName
        if(name.text.toString().isBlank()){
            Toast.makeText(this,"Enter value!",Toast.LENGTH_LONG).show()
        }else{
            viewModel.add(Blog(name.text.toString().trim()))
            name.text.clear()
        }
    }

    private fun setUpObserver() {
        viewModel._nameList.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    private fun initViews() {
        nameList = ArrayList()
    }

    private fun setRecyclerView() {
        binding.rvNames.layoutManager =LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = NamesAdapter(nameList,this)
        binding.rvNames.adapter = adapter
    }

    override fun <T> onItemDelete(item: T) {
        if (item is Blog){
            viewModel.remove(item)
        }
    }


}