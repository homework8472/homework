package com.musinsa.category.domain.service

import com.musinsa.category.adapter.ProductAdapter
import com.musinsa.category.domain.model.type.OrderType
import com.musinsa.category.fixtures.*
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class ProductDomainServiceTest {
    private val brandDomainService = mockk<BrandDomainService>()
    private val categoryDomainService = mockk<CategoryDomainService>()
    private val productAdapter = mockk<ProductAdapter>()
    private val service = ProductDomainService(productAdapter, brandDomainService, categoryDomainService)

    @Test
    fun `가장 저렴한 상품 조회 - 카테고리별로 최저가 상품 리스트 조회`(){
        every { categoryDomainService.findAll() } returns categories

        categoryDomainService.findAll().map {
            every { productAdapter.findCheapestProducts(it.id) } returns products.filter { p -> p.category == it }.minBy { it.price }!!
        }

        service.findCheapestProducts() shouldBe cheapestCategoryProducts
    }

    @Test
    fun `가장 저렴한 상품 조회 - 카테고리가 하나도 없는 경우`() {
        every { categoryDomainService.findAll() } returns emptyList()

        service.findCheapestProducts() shouldBe emptyList()
    }

    @Test
    fun `모든 상품 조회`() {
        every { brandDomainService.findAll() } returns brands

        brands.forEach { brand ->
            every { productAdapter.findAllProducts(brand) } returns products.filter { it.brand == brand }
        }

        service.findAllProducts() shouldBe brandProducts
    }

    @Test
    fun `findAllTopOrderByPrice 조회`(){
        every { productAdapter.findAllTopOrderByPrice(any(), eq(OrderType.ASC)) } returns listOf(lower)
        every { productAdapter.findAllTopOrderByPrice(any(), eq(OrderType.DESC)) } returns listOf(higher)


        service.findAllTopOrderByPrice(categories.first().id, OrderType.ASC) shouldBe listOf(lower)
        service.findAllTopOrderByPrice(categories.first().id, OrderType.DESC) shouldBe listOf(higher)
    }
}