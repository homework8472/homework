package com.musinsa.category.domain.model

data class Product(
    val id: Long,
    val category: Category,
    val brand: Brand,
    val price: Long,
)

fun List<Product>.totalPrice(): Long = this.sumOf { it.price }