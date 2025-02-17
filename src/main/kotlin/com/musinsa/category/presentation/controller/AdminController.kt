package com.musinsa.category.presentation.controller

import com.musinsa.category.domain.model.Product
import com.musinsa.category.presentation.dto.request.CreateProductRequest
import com.musinsa.category.presentation.dto.request.UpdateProductRequest
import com.musinsa.category.domain.service.ProductAdminDomainService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin/products")
class AdminController(
    private val productAdminDomainService: ProductAdminDomainService
) {

    @PostMapping
    fun create(@RequestBody request: CreateProductRequest): Product {
        return productAdminDomainService.create(request)
    }

    @PutMapping("/{productId}")
    fun update(
        @PathVariable productId: Long,
        @RequestBody request: UpdateProductRequest
    ): Product {
        return productAdminDomainService.update(productId, request)
    }

    @DeleteMapping("/{productId}")
    fun delete(
        @PathVariable productId: Long
    ): Product {
        return productAdminDomainService.delete(productId)
    }
}