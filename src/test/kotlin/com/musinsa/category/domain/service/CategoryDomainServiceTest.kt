package com.musinsa.category.domain.service

import com.musinsa.category.adapter.CategoryAdapter
import com.musinsa.category.fixtures.categories
import com.musinsa.category.support.exception.EntityNotFoundError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class CategoryDomainServiceTest {
    private val categoryAdapter = mockk<CategoryAdapter>()
    private val service = CategoryDomainService(categoryAdapter)

    @Test
    fun `모든 카테고리를 조회한다`() {
        every { categoryAdapter.findAll() } returns categories

        service.findAll() shouldBe categories
    }

    @Test
    fun `이름으로 카테고리를 조회한다`() {
        val category = categories.first()
        every { categoryAdapter.findByName(category.name) } returns category

        service.findByName(category.name) shouldBe category

    }

    @Test
    fun `존재하지 않는 카테고리 이름으로 조회시 예외가 발생한다`() {
        val categoryName = "전자제품"
        every { categoryAdapter.findByName(categoryName) } returns null

        shouldThrow<EntityNotFoundError> {
            service.findByName(categoryName)
        }.message shouldBe "$categoryName 카테고리를 찾을 수 없습니다."
    }
}