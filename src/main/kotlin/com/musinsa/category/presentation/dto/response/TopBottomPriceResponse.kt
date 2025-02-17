package com.musinsa.category.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.musinsa.category.domain.model.Category
import com.musinsa.category.domain.model.Product

data class TopBottomPriceResponse(
    @JsonProperty("카테고리")
    val category: String,
    @JsonProperty("최저가")
    val bottom1: List<TopBottomProductResponse>,
    @JsonProperty("최고가")
    val top1: List<TopBottomProductResponse>,
) {
    constructor(category: Category, bottoms: List<Product>, tops: List<Product>) : this(
        category.name,
        bottoms.map { TopBottomProductResponse(it.id, it.brand.name, it.price) },
        tops.map { TopBottomProductResponse(it.id, it.brand.name, it.price) },
    )
}

data class TopBottomProductResponse(
    val id: Long,
    @JsonProperty("브랜드")
    val brand: String,
    @JsonProperty("가격")
    val price: Long,
)