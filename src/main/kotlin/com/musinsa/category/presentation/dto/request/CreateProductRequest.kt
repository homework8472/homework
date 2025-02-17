package com.musinsa.category.presentation.dto.request

data class CreateProductRequest(
    val category: String,
    val brand: String,
    val price: Long
)