package com.musinsa.category.adapter

import com.musinsa.category.fixtures.brandJpaEntities
import com.musinsa.category.mapper.toDomain
import com.musinsa.category.persistence.entity.BrandJpaEntity
import com.musinsa.category.persistence.repository.BrandRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class BrandAdapterTest {

    private val repository: BrandRepository = mockk<BrandRepository>()
    private val adapter = BrandAdapter(repository)

    @Test
    fun `모든 브랜드 조회`() {
        every { repository.findAll() } returns brandJpaEntities

        adapter.findAll() shouldBe brandJpaEntities.map { it.toDomain() }
    }

    @Test
    fun `브랜드 생성`() {
        val entity = BrandJpaEntity(id = 1, name = "TestBrand")
        every { repository.save(any()) } returns entity

        adapter.create(entity.name) shouldBe entity.toDomain()
    }

    @Test
    fun `이름으로 브랜드 조회`() {
        val entity = BrandJpaEntity(id = 1, name = "TestBrand")
        every { repository.findByName(any()) } returns entity

        adapter.findByName(entity.name) shouldBe entity.toDomain()
    }

    @Test
    fun `존재하지 않는 이름으로 브랜드 조회시 null 반환`() {
        every { repository.findByName(any()) } returns null

        adapter.findByName("brandName") shouldBe null
    }

    @Test
    fun `존재하는 브랜드 조회 또는 생성`() {
        val entity = BrandJpaEntity(id = 1, name = "TestBrand")
        every { repository.findByName(any()) } returns entity

        adapter.findOrCreate(entity.name) shouldBe entity.toDomain()
    }

    @Test
    fun `존재하지 않는 브랜드 조회 또는 생성`() {
        val entity = BrandJpaEntity(id = 1, name = "TestBrand")

        every { repository.findByName(any()) } returns null
        every { repository.save(any()) } returns entity

        adapter.findOrCreate(entity.name) shouldBe entity.toDomain()
    }
}