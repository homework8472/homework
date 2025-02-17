package com.musinsa.category.domain.model

data class BrandProduct(
    val brand: Brand,
    val products: List<Product>
) {
    fun isProductEmpty(): Boolean {
        return products.isEmpty()
    }

    fun totalPrice(): Long {
        return products.sumOf { it.price }
    }
}