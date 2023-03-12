package com.meghamlabs.restaurantmanagementuserapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.meghamlabs.restaurantmanagementuserapp.databinding.OrderConfirmBinding

class FinalActivity : AppCompatActivity() {

    private lateinit var binding: OrderConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the Parcelable list of Food objects from the intent
        val foodList = intent.getParcelableArrayListExtra<Food>("food_list")!!

        // Create an instance of your FoodAdapter and set it as the adapter of the RecyclerView
        val adapter = FinalAdapter(foodList)
        binding.recyclerViewFinal.adapter = adapter

        // Set a LinearLayoutManager as the layout manager for the RecyclerView
        binding.recyclerViewFinal.layoutManager = LinearLayoutManager(this)

        binding.btnOrderAgain.setOnClickListener{

            gotoMainActivity()

        }
    }

    private fun gotoMainActivity() {
       finish()
    }
}
