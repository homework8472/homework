package com.musinsa.category.adapter

import com.musinsa.category.mapper.toDomain
import com.musinsa.category.persistence.entity.CategoryJpaEntity
import com.musinsa.category.persistence.repository.CategoryRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test

class CategoryAdapterTest {

    private val repository: CategoryRepository = mockk<CategoryRepository>()
    private val adapter = CategoryAdapter(repository)

    @Test
    fun `모든 카테고리 조회`() {
        val entities = listOf(
            CategoryJpaEntity(id = 1, name = "TOP"),
            CategoryJpaEntity(id = 2, name = "BOTTOM")
        )
        every { repository.findAll() } returns entities

        adapter.findAll() shouldBe entities.map { it.toDomain() }
    }

    @Test
    fun `이름으로 카테고리 조회`() {
        val entity = CategoryJpaEntity(id = 1, name = "TOP")
        every { repository.findByName(any()) } returns entity

        adapter.findByName(entity.name) shouldBe entity.toDomain()
    }

    @Test
    fun `존재하지 않는 이름으로 카테고리 조회시 null 반환`() {
        every { repository.findByName(any()) } returns null

        adapter.findByName("categoryName") shouldBe null
    }

    @Test
    fun `카테고리 생성`() {
        val entity = CategoryJpaEntity(id = 1, name = "TOP")
        every { repository.save(any()) } returns entity

        adapter.create(entity.name) shouldBe entity.toDomain()
    }

    @Test
    fun `존재하는 카테고리 조회 또는 생성`() {
        val entity = CategoryJpaEntity(id = 1, name = "TOP")
        every { repository.findByName(any()) } returns entity

        adapter.findOrCreate(entity.name) shouldBe entity.toDomain()
    }

    @Test
    fun `존재하지 않는 카테고리 조회 또는 생성`() {
        val entity = CategoryJpaEntity(id = 1, name = "TOP")

        every { repository.findByName(any()) } returns null
        every { repository.save(any()) } returns entity

        adapter.findOrCreate(entity.name) shouldBe entity.toDomain()
    }
}