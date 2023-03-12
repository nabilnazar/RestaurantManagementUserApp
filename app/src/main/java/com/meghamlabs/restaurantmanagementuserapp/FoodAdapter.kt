package com.meghamlabs.restaurantmanagementuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class FoodAdapter(var foodList: List<Food>, var totalQuantityListener: TotalQuantityListener):
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    var totalQuantity: Int  = 0

    class FoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val foodNameTextView: TextView = itemView.findViewById(R.id.food_name)
        val qtyTextView: TextView = itemView.findViewById(R.id.qty)
        val addQtyButton: ImageButton = itemView.findViewById(R.id.add_button)
        val removeQtyButton: ImageButton = itemView.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]

        var flag = 0

        holder.foodNameTextView.text = food.name
        holder.qtyTextView.text = food.quantity.toString()

        holder.addQtyButton.setOnClickListener {
            if(flag == 0)
            {food.orderedQty++}
            flag = 1
            food.quantity++
            holder.qtyTextView.text = food.quantity.toString()
            updateTotalQuantity()
        }

        holder.removeQtyButton.setOnClickListener {
            if (food.quantity > 0) {
                food.quantity--
                holder.qtyTextView.text = food.quantity.toString()
                updateTotalQuantity()
            }
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    private fun updateTotalQuantity() {
        totalQuantity = foodList.sumOf { it.quantity}
        totalQuantityListener.onTotalQuantityUpdated(totalQuantity)
    }

}

