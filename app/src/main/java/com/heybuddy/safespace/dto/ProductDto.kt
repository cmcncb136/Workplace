package com.heybuddy.safespace.dto

data class ProductDto (
    val productId: String,
    val productName: String,
    val providerId: String,
    val imgPath: String,
    val category: CategoryDto,
    val description: String,
    val capacity: Integer,
    val month: Integer,
    val price: Integer
)
