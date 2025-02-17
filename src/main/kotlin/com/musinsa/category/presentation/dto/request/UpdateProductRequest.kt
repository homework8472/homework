package com.musinsa.category.presentation.dto.request

data class UpdateProductRequest(
    val category: String,
    val brand: String,
    val price: Long
)