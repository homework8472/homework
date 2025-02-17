package com.musinsa.category.adapter

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.domain.model.type.OrderType
import com.musinsa.category.fixtures.*
import com.musinsa.category.mapper.toDomain
import com.musinsa.category.mapper.toEntity
import com.musinsa.category.persistence.repository.ProductRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*
import kotlin.test.Test

class ProductAdapterTest {

    private val repository: ProductRepository = mockk<ProductRepository>()
    private val adapter = ProductAdapter(repository)


    @Test
    fun `ID로 상품 조회`() {
        every { repository.findById(any()) } returns Optional.of(productTopC.toEntity())

        adapter.findById(productTopC.id) shouldBe productTopC
    }

    @Test
    fun `존재하지 않는 ID로 상품 조회`() {
        every { repository.findById(any()) } returns Optional.empty()

        adapter.findById(999) shouldBe null
    }

    @Test
    fun `상품 생성`() {
        every { repository.save(any()) } returns productJpaEntity

        adapter.create(category, brand, price) shouldBe productJpaEntity.toDomain()
    }

    @Test
    fun `상품 수정`() {
        every { repository.save(any()) } returns productJpaEntity

        adapter.update(productJpaEntity.id, category, brand, price) shouldBe productJpaEntity.toDomain()
    }

    @Test
    fun `상품 삭제`() {
        every { repository.deleteById(any()) } returns Unit
        adapter.delete(1L) shouldBe Unit
    }

    @Test
    fun `특정 카테고리의 최저가 상품 조회`(){
        every { repository.findTopByCategoryIdOrderByPriceAsc(any()) } returns productTopC.toEntity()

        adapter.findCheapestProducts(categories.first().id) shouldBe productTopC
    }

    @Test
    fun `특정 카테고리의 최저가 상품 조회 - 상품이 없을경우`(){
        every { repository.findTopByCategoryIdOrderByPriceAsc(any()) } returns null

        adapter.findCheapestProducts(categories.first().id) shouldBe null
    }

    @Test
    fun `특정 브랜드의 모든 제품 조회`() {
        every { repository.findAllByBrand(any()) } returns productsByBrandD.map { it.toEntity() }

        adapter.findAllProducts(Brand(4, "D")) shouldBe productsByBrandD
    }

    @Test
    fun `특정 카테고리의 가장 저렴하거나 비싼 상품 조회`(){
        every { repository.findTopByCategoryIdOrderByPriceAsc(any()) } returns lower.toEntity()
        every { repository.findTopByCategoryIdOrderByPriceDesc(any()) } returns higher.toEntity()
        every { repository.findAllByCategoryIdAndPriceOrderByPriceAsc(any(), any()) } returns listOf(lower.toEntity())
        every { repository.findAllByCategoryIdAndPriceOrderByPriceDesc(any(), any()) } returns listOf(higher.toEntity())


        adapter.findAllTopOrderByPrice(categories.first().id, OrderType.ASC) shouldBe listOf(lower)
        adapter.findAllTopOrderByPrice(categories.first().id, OrderType.DESC) shouldBe listOf(higher)
    }

    @Test
    fun `특정 카테고리의 가장 저렴하거나 비싼 상품 조회 - 상품이 없을 경우`(){
        every { repository.findTopByCategoryIdOrderByPriceAsc(any()) } returns null
        every { repository.findTopByCategoryIdOrderByPriceDesc(any()) } returns null

        adapter.findAllTopOrderByPrice(categories.first().id, OrderType.ASC) shouldBe emptyList()
        adapter.findAllTopOrderByPrice(categories.first().id, OrderType.DESC) shouldBe emptyList()
    }
//
//    @Test
//    fun `구현 3 - 특정 카테고리의 가장 저렴한 상품과 가장 비싼 상품 조회 - 같은 가격이 여러개 있을경우`(){
//        every { repository.findTopByCategoryIdOrderByPriceAsc(any()) } returns lower.toEntity()
//        every { repository.findTopByCategoryIdOrderByPriceDesc(any()) } returns higher.toEntity()
//        every { repository.findAllByCategoryIdAndPriceOrderByPriceAsc(any(), any()) } returns listOf(lower.toEntity(), lower2.toEntity())
//        every { repository.findAllByCategoryIdAndPriceOrderByPriceDesc(any(), any()) } returns listOf(higher.toEntity())
//
//        service.findTopBottomProducts(categories.first()) shouldBe Pair(listOf(lower, lower2), listOf(higher))
//    }
//
//    @Test
//    fun `구현 3 - 특정 카테고리의 가장 저렴한 상품과 가장 비싼 상품이 같을경우 조회`(){
//        every { repository.findTopByCategoryIdOrderByPriceAsc(any()) } returns lower.toEntity()
//        every { repository.findTopByCategoryIdOrderByPriceDesc(any()) } returns lower.toEntity()
//        every { repository.findAllByCategoryIdAndPriceOrderByPriceAsc(any(), any()) } returns listOf(lower.toEntity())
//        every { repository.findAllByCategoryIdAndPriceOrderByPriceDesc(any(), any()) } returns listOf(lower.toEntity())
//
//        service.findTopBottomProducts(categories.first()) shouldBe Pair(listOf(lower), listOf(lower))
//    }

}