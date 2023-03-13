package com.meghamlabs.restaurantmanagementuserapp

data class Order( val id: String= "",val tableNumber: Int = 0, val food: MutableList<Food>)