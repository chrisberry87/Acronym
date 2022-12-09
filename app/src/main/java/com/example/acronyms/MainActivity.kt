package com.example.acronyms

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acronyms.databinding.ActivityMainBinding
import com.example.acronyms.repository.AbbItem
import com.example.acronyms.repository.Lf
import com.example.acronyms.repository.RetrofitInstance
import com.example.acronyms.ui.MeaningAdapter
import com.example.acronyms.viewmodel.MainViewModel
import com.example.acronyms.viewmodel.MainViewModelFactory
import com.google.android.material.internal.ViewUtils.hideKeyboard

const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: MainViewModel
    private val meaningAdapter: MeaningAdapter = MeaningAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        viewmodel = ViewModelProvider(this, MainViewModelFactory(RetrofitInstance.api)).get(MainViewModel::class.java)

        binding.searchBtn.setOnClickListener {
            if (binding.acronyms.text.length == 0) {
                binding.acronyms.setError("Please input keyword")
                return@setOnClickListener
            }
            binding.progressBar.isVisible = true
            viewmodel.getMeaningData()
        }
        binding.viewmodel = viewmodel

        viewmodel.meaningsData.observe(this, Observer {
            val meanings: List<AbbItem> = it
            if(meanings.size == 0){
                binding.recyclerView.visibility = View.GONE
                binding.nodataView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.nodataView.visibility = View.GONE
                meaningAdapter.setAcromineList(it[0].lfs)
            }
            binding.progressBar.isVisible = false
        })

        viewmodel.errorMessage.observe(this, Observer {
            if(it != "SUCCESS") {
                Toast.makeText(getApplicationContext(),
                    it, Toast.LENGTH_SHORT)
                    .show();
            }
            binding.progressBar.isVisible = false
        })
    }

    fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = meaningAdapter
    }

}