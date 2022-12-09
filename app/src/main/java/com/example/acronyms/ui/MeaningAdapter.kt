package com.example.acronyms.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acronyms.databinding.ItemMeaningBinding
import com.example.acronyms.repository.AbbItem
import com.example.acronyms.repository.Lf

class MeaningAdapter : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    var meaningList = mutableListOf<Lf>()

    fun setAcromineList(modelList: List<Lf>) {
        this.meaningList = modelList.toMutableList()
        notifyDataSetChanged()
    }

    inner class MeaningViewHolder(val binding: ItemMeaningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lf: Lf) {
            binding.lf = lf
        }
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE","" + this.meaningList.size)
        return this.meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        val meaning = meaningList[position]
        holder.bind(meaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        return MeaningViewHolder(ItemMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}