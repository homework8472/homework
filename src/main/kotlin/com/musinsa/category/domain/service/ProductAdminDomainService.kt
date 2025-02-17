package com.musinsa.category.domain.service

import com.musinsa.category.domain.model.Product
import com.musinsa.category.presentation.dto.request.CreateProductRequest
import com.musinsa.category.presentation.dto.request.UpdateProductRequest
import com.musinsa.category.support.exception.NotFoundError
import com.musinsa.category.adapter.BrandAdapter
import com.musinsa.category.adapter.CategoryAdapter
import com.musinsa.category.adapter.ProductAdapter
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProductAdminDomainService(
    private val categoryAdapter: CategoryAdapter,
    private val brandAdapter: BrandAdapter,
    private val productAdapter: ProductAdapter
) {

    @Transactional
    fun create(request: CreateProductRequest): Product {
        val category = categoryAdapter.findOrCreate(request.category)
        val brand = brandAdapter.findOrCreate(request.brand)

        return productAdapter.create(category, brand, request.price)
    }

    @Transactional
    fun update(productId: Long, request: UpdateProductRequest): Product {
        val product = productAdapter.findById(productId)
            ?: throw NotFoundError(message = "productId: $productId 을 찾을 수 없습니다.")

        val category = categoryAdapter.findByName(request.category) ?: throw NotFoundError(message = "${request.category} 카테고리를 찾을 수 없습니다.")
        val brand = brandAdapter.findByName(request.brand) ?: throw NotFoundError(message = "${request.brand} 브랜드를 찾을 수 없습니다.")

        return productAdapter.update(product.id, category, brand, request.price)
    }

    @Transactional
    fun delete(productId: Long): Product {
        val product = productAdapter.findById(productId)
            ?: throw NotFoundError(message = "productId: $productId 을 찾을 수 없습니다.")

        return productAdapter.delete(product.id).let {
            product
        }
    }
}