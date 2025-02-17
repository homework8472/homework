package com.musinsa.category.domain.service

import com.musinsa.category.adapter.BrandAdapter
import com.musinsa.category.fixtures.brands
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class BrandDomainServiceTest {
    private val brandAdapter = mockk<BrandAdapter>()
    private val service = BrandDomainService(brandAdapter)

    @Test
    fun `모든 브랜드를 조회한다`() {
        every { brandAdapter.findAll() } returns brands

        service.findAll() shouldBe brands

    }
}