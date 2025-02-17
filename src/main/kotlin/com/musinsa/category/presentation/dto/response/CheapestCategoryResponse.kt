package com.musinsa.category.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.musinsa.category.domain.model.Product

data class CheapestCategoryResponse(
    @JsonProperty("최저가")
    val products: List<CheapestCategoryProductResponse>,
    @JsonProperty("총액")
    val total: Long = products.sumOf { it.price }
) {
    constructor(products: List<Product>) : this(
        products = products.map { CheapestCategoryProductResponse(it) }
    )
}

data class CheapestCategoryProductResponse(
    val id: Long,
    @JsonProperty("카테고리")
    val category: String,
    @JsonProperty("브랜드")
    val brand: String,
    @JsonProperty("가격")
    val price: Long
) {
    constructor(product: Product) : this(
        id = product.id,
        category = product.category.name,
        brand = product.brand.name,
        price = product.price
    )
}