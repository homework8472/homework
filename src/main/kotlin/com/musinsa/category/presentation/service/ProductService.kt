package com.musinsa.category.presentation.service

import com.musinsa.category.domain.model.BrandProduct
import com.musinsa.category.domain.model.type.OrderType
import com.musinsa.category.domain.service.CategoryDomainService
import com.musinsa.category.domain.service.ProductDomainService
import com.musinsa.category.presentation.dto.response.CheapestBrandResponse
import com.musinsa.category.presentation.dto.response.CheapestCategoryResponse
import com.musinsa.category.presentation.dto.response.TopBottomPriceResponse
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productDomainService: ProductDomainService,
    private val categoryDomainService: CategoryDomainService,
) {
    fun findCheapestProducts(): CheapestCategoryResponse {
        return CheapestCategoryResponse(productDomainService.findCheapestProducts())
    }

    fun findCheapestBrandProducts(): CheapestBrandResponse {
        val bpList = productDomainService.findAllProducts()
            .filterNot(BrandProduct::isProductEmpty) // 상품이 없는 브랜드는 제외 할 경우
        val minPrice: Long = bpList.minOf(BrandProduct::totalPrice)

        return CheapestBrandResponse.of(bpList.filter { it.totalPrice() == minPrice })
    }

    fun findTopBottomProducts(categoryName: String): TopBottomPriceResponse {
        return categoryDomainService.findByName(categoryName).let { category ->
            TopBottomPriceResponse(category,
                productDomainService.findAllTopOrderByPrice(category.id, OrderType.ASC),
                productDomainService.findAllTopOrderByPrice(category.id, OrderType.DESC),
            )
        }
    }
}