package com.musinsa.category.presentation.domain

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.fixtures.*
import com.musinsa.category.domain.model.BrandProduct
import com.musinsa.category.domain.model.type.OrderType
import com.musinsa.category.domain.model.Product
import com.musinsa.category.domain.service.CategoryDomainService
import com.musinsa.category.domain.service.ProductDomainService
import com.musinsa.category.presentation.dto.response.CheapestCategoryResponse
import com.musinsa.category.presentation.service.ProductService
import com.musinsa.category.support.exception.EntityNotFoundError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class ProductServiceTest {
    private val categoryDomainService = mockk<CategoryDomainService>()
    private val productDomainSerivce = mockk<ProductDomainService>()
    private val service = ProductService(productDomainSerivce, categoryDomainService)

    @Test
    fun `구현 1 - 카테고리별로 최저가 상품 리스트 조회`(){

        every { productDomainSerivce.findCheapestProducts() } returns cheapestCategoryProducts

        service.findCheapestProducts() shouldBe cheapestCategoryResponse
    }

    @Test
    fun `구현 1 - 카테고리가 하나도 없는 경우`() {
        every { productDomainSerivce.findCheapestProducts() } returns emptyList()

        service.findCheapestProducts() shouldBe CheapestCategoryResponse(emptyList<Product>())
    }

    @Test
    fun `구현 2 - 가장 싼 단일 브랜드 상품 조회`(){
        every { productDomainSerivce.findAllProducts() } returns brandProducts

        service.findCheapestBrandProducts() shouldBe cheapestBrandResponse
    }

    @Test
    fun `구현 2 - 가장 싼 단일 브랜드 상품 조회 - 특정 브랜드가 상품이 없는 경우 재외하고 싶을때`(){
        every { productDomainSerivce.findAllProducts() } returns brandProducts + BrandProduct(Brand(10, "상품이 없는 브랜드"), emptyList())

        service.findCheapestBrandProducts() shouldBe cheapestBrandResponse
    }

    @Test
    fun `구현 3 - 가장 저렴한 상품과 가장 비싼 상품 - 카테고리가 없는 경우`(){
        every { categoryDomainService.findByName(any()) } throws EntityNotFoundError(message = "카테고리를 찾을 수 없습니다.")
        every { productDomainSerivce.findAllTopOrderByPrice(any(), eq(OrderType.ASC)) } returns listOf(lower)
        every { productDomainSerivce.findAllTopOrderByPrice(any(), eq(OrderType.DESC)) } returns listOf(higher)

        shouldThrow<EntityNotFoundError> {
            service.findTopBottomProducts(categories.first().name)
        }
    }

    @Test
    fun `구현 3 - 가장 저렴한 상품과 가장 비싼 상품`(){
        every { categoryDomainService.findByName(any()) } returns categories.first()
        every { productDomainSerivce.findAllTopOrderByPrice(any(), eq(OrderType.ASC)) } returns listOf(lower)
        every { productDomainSerivce.findAllTopOrderByPrice(any(), eq(OrderType.DESC)) } returns listOf(higher)

        service.findTopBottomProducts(categories.first().name) shouldBe topBottomPriceResponse
    }

    @Test
    fun `구현 3 - 가장 저렴한 상품과 가장 비싼 상품이 같을경우`(){
        every { categoryDomainService.findByName(any()) } returns categories.first()
        every { productDomainSerivce.findAllTopOrderByPrice(any(), eq(OrderType.ASC)) } returns listOf(lower)
        every { productDomainSerivce.findAllTopOrderByPrice(any(), eq(OrderType.DESC)) } returns listOf(lower)

        service.findTopBottomProducts(categories.first().name) shouldBe top1Bottom1SameResponse
    }
}