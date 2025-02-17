package com.musinsa.category.domain.service

import com.musinsa.category.adapter.BrandAdapter
import com.musinsa.category.adapter.CategoryAdapter
import com.musinsa.category.adapter.ProductAdapter
import com.musinsa.category.domain.model.Brand
import com.musinsa.category.domain.model.Category
import com.musinsa.category.domain.model.Product
import com.musinsa.category.presentation.dto.request.CreateProductRequest
import com.musinsa.category.presentation.dto.request.UpdateProductRequest
import com.musinsa.category.support.exception.NotFoundError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ProductAdminDomainServiceTest {
    private val categoryAdapter = mockk<CategoryAdapter>()
    private val brandAdapter = mockk<BrandAdapter>()
    private val productAdapter = mockk<ProductAdapter>()
    private val service = ProductAdminDomainService(categoryAdapter, brandAdapter, productAdapter)

    private val category = Category(1, "TOP")
    private val brand = Brand(1, "A")
    private val product = Product(1, category, brand, 10000)

    @Test
    fun `상품 생성`() {
        val request = CreateProductRequest("TOP", "A", 10000)

        every { categoryAdapter.findOrCreate("TOP") } returns category
        every { brandAdapter.findOrCreate("A") } returns brand
        every { productAdapter.create(category, brand, 10000) } returns product

        service.create(request) shouldBe product
    }

    @Test
    fun `상품 수정`() {
        val request = UpdateProductRequest("TOP", "A", 20000)

        every { productAdapter.findById(1) } returns product
        every { categoryAdapter.findByName("TOP") } returns category
        every { brandAdapter.findByName("A") } returns brand
        every { productAdapter.update(1, category, brand, 20000) } returns product.copy(price = 20000)

        service.update(1, request) shouldBe product.copy(price = 20000)
    }

    @Test
    fun `존재하지 않는 상품 수정시 예외 발생`() {
        val request = UpdateProductRequest("TOP", "A", 20000)
        every { productAdapter.findById(999) } returns null

        shouldThrow<NotFoundError> {
            service.update(999, request)
        }
    }

    @Test
    fun `존재하지 않는 카테고리로 상품 수정시 예외 발생`() {
        val request = UpdateProductRequest("INVALID", "A", 20000)
        
        every { productAdapter.findById(1) } returns product
        every { categoryAdapter.findByName("INVALID") } returns null

        shouldThrow<NotFoundError> {
            service.update(1, request)
        }
    }

    @Test
    fun `존재하지 않는 브랜드로 상품 수정시 예외 발생`() {
        val request = UpdateProductRequest("TOP", "INVALID", 20000)
        
        every { productAdapter.findById(1) } returns product
        every { categoryAdapter.findByName("TOP") } returns category
        every { brandAdapter.findByName("INVALID") } returns null

        shouldThrow<NotFoundError> {
            service.update(1, request)
        }
    }

    @Test
    fun `상품 삭제`() {
        every { productAdapter.findById(1) } returns product
        every { productAdapter.delete(1) } returns Unit

        service.delete(1) shouldBe product
    }

    @Test
    fun `존재하지 않는 상품 삭제시 예외 발생`() {
        every { productAdapter.findById(999) } returns null

        shouldThrow<NotFoundError> {
            service.delete(999)
        }
    }
}