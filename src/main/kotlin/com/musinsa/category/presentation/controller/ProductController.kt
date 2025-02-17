package com.musinsa.category.presentation.controller

import com.musinsa.category.presentation.dto.response.CheapestBrandResponse
import com.musinsa.category.presentation.dto.response.CheapestCategoryResponse
import com.musinsa.category.presentation.dto.response.TopBottomPriceResponse
import com.musinsa.category.presentation.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/cheapest")
    fun getCheapestProducts(): CheapestCategoryResponse {
        return productService.findCheapestProducts()
    }

    @GetMapping("/cheapest/brands")
    fun getCheapestBrandProducts(): CheapestBrandResponse {
        return productService.findCheapestBrandProducts()
    }

    @GetMapping("/topbottom")
    fun getTopBottom(
        @RequestParam category: String
    ): TopBottomPriceResponse {
        return productService.findTopBottomProducts(category)
    }

}