package com.musinsa.category.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.musinsa.category.domain.model.BrandProduct
import com.musinsa.category.domain.model.Product

data class CheapestBrandResponse(
    @JsonProperty("최저가")
    val cheapestCategoryInnerResponse: List<CheapestCategoryInnerResponse>,
) {
    companion object {
        fun of(brands: List<BrandProduct>) = CheapestBrandResponse(
            cheapestCategoryInnerResponse = brands.map {
                CheapestCategoryInnerResponse(
                    brand = it.brand.name,
                    products = it.products.map { CheapestBrandProductResponse(it) }
                )
            }
        )
    }
}

data class CheapestCategoryInnerResponse(
    @JsonProperty("브랜드")
    val brand: String,
    @JsonProperty("카테고리")
    val products: List<CheapestBrandProductResponse>,
    @JsonProperty("총액")
    val total: Long = products.sumOf { it.price }
)

data class CheapestBrandProductResponse(
    val id: Long,
    @JsonProperty("카테고리")
    val category: String,
    @JsonProperty("가격")
    val price: Long
) {
    constructor(product: Product) : this(
        id = product.id,
        category = product.category.name,
        price = product.price
    )
}