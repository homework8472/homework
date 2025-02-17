package com.musinsa.category.domain.service

import com.musinsa.category.adapter.ProductAdapter
import com.musinsa.category.domain.model.BrandProduct
import com.musinsa.category.domain.model.type.OrderType
import com.musinsa.category.domain.model.Product
import org.springframework.stereotype.Service

@Service
class ProductDomainService(
    private val productAdapter: ProductAdapter,
    private val brandDomainService: BrandDomainService,
    private val categoryDomainService: CategoryDomainService
) {
    fun findCheapestProducts(): List<Product> {
        return categoryDomainService.findAll().mapNotNull {
            productAdapter.findCheapestProducts(it.id)
        }
    }

    fun findAllProducts(): List<BrandProduct> {
        return brandDomainService.findAll().map {
            BrandProduct(it, productAdapter.findAllProducts(it))
        }
    }

    fun findAllTopOrderByPrice(id: Long, asc: OrderType): List<Product> {
        return productAdapter.findAllTopOrderByPrice(id, asc)
    }
}