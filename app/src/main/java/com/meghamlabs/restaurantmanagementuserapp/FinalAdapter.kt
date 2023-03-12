package com.meghamlabs.restaurantmanagementuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meghamlabs.restaurantmanagementuserapp.databinding.FoodItemBinding


class FinalAdapter(private val foodList: List<Food>) : RecyclerView.Adapter<FinalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class ViewHolder(private val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.finalFoodName.text = food.name
            binding.finalQty.text = food.quantity.toString()
        }
    }
}
