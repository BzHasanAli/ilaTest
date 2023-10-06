package com.example.ilatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ilatest.R
import com.example.ilatest.databinding.CarouselItemBinding

class CarouselAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<CarouselAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding: CarouselItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setImage(imageRes: Int) {
            binding.ivImage.setImageResource(imageRes)
        }
    }

    override fun getItemCount(): Int = imageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setImage(imageList[position])
    }
}

