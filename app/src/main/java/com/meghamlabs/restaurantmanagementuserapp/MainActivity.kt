package com.meghamlabs.restaurantmanagementuserapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.meghamlabs.restaurantmanagementuserapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), TotalQuantityListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinner:Spinner
    private lateinit var button_down: ImageView
    private lateinit var button_up: ImageView
    val database = Firebase.database
    val orderRef = database.getReference("orders")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmButton.setOnClickListener{
            buttonConfirmPressed()
        }

        button_down = binding.downArrow
        button_up = binding.upArrow
        button_down.setOnClickListener{
            binding.downArrow.visibility = View.GONE
            binding.upArrow.visibility = View.VISIBLE
            buttonPressedDownShowList()
        }

        button_up.setOnClickListener{
            binding.upArrow.visibility = View.GONE
            binding.downArrow.visibility = View.VISIBLE
            buttonPressedUpHideList()
        }



         spinner = binding.tableSpinner

        val tableNumbers = listOf(1, 2, 3, 4, 5, 6)
        val adapter_array = ArrayAdapter(this, android.R.layout.simple_spinner_item, tableNumbers)
        adapter_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter_array

        //val tableNumber = spinner.selectedItem as Int
        val database = Firebase.database
        val foodRef = database.getReference("foods")

        val foodList = mutableListOf<Food>()

        foodRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                for (foodSnapshot in snapshot.children) {
                    val food = foodSnapshot.getValue(Food::class.java)
                    if (food != null) {
                        foodList.add(food)
                    }
                }

                // create and set adapter for RecyclerView
                val adapter = FoodAdapter(foodList, this@MainActivity)
                binding.recyclerView.adapter = adapter
                adapter.totalQuantityListener =  this@MainActivity

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }

        })

    }

    private fun buttonConfirmPressed() {

        val tableNumber = spinner.selectedItem as Int

        // Iterate over the food items in the adapter to retrieve their names and quantities
        val foodList = mutableListOf<Food>()
        val adapter = binding.recyclerView.adapter as FoodAdapter
        for (food in adapter.foodList) {
            if (food.quantity > 0) {
                foodList.add(Food(food.name,food.quantity))
                val order = Order(tableNumber, food.name, food.quantity)
                orderRef.push().setValue(order)
            }
        }

        // Launch the FinalActivity and pass the table number and food list as extras
        val intent = Intent(this, FinalActivity::class.java)
        intent.putParcelableArrayListExtra("food_list", ArrayList(foodList))
        startActivity(intent)
    }

    private fun buttonPressedDownShowList() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.visibility = View.VISIBLE;
    }

    private fun buttonPressedUpHideList() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.visibility = View.GONE

    }

    override fun onTotalQuantityUpdated(quantity: Int) {
        binding.totalNumberOfSelectedItem.text = "Total ${quantity} Items added"
    }




}