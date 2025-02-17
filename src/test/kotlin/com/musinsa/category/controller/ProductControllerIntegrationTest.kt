package com.musinsa.category.controller

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.NestedTestConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ProductControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `구현 1 - 카테고리별 최저가 상품 조회`() {
        val result = mockMvc.perform(get("/api/v1/products/cheapest"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        result shouldBe """
                {"최저가":[{"id":17,"카테고리":"상의","브랜드":"C","가격":10000},{"id":34,"카테고리":"아우터","브랜드":"E","가격":5000},{"id":27,"카테고리":"바지","브랜드":"D","가격":3000},{"id":4,"카테고리":"스니커즈","브랜드":"A","가격":9000},{"id":5,"카테고리":"가방","브랜드":"A","가격":2000},{"id":30,"카테고리":"모자","브랜드":"D","가격":1500},{"id":71,"카테고리":"양말","브랜드":"I","가격":1700},{"id":48,"카테고리":"악세서리","브랜드":"F","가격":1900}],"총액":34100}
            """.trimIndent()
    }

    @Test
    fun `구현 2 - 가장 싼 단일 브랜드 상품 조회`(){
        val result = mockMvc.perform(get("/api/v1/products/cheapest/brands"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        result shouldBe """
                {"최저가":[{"브랜드":"D","카테고리":[{"id":25,"카테고리":"상의","가격":10100},{"id":26,"카테고리":"아우터","가격":5100},{"id":27,"카테고리":"바지","가격":3000},{"id":28,"카테고리":"스니커즈","가격":9500},{"id":29,"카테고리":"가방","가격":2500},{"id":30,"카테고리":"모자","가격":1500},{"id":31,"카테고리":"양말","가격":2400},{"id":32,"카테고리":"악세서리","가격":2000}],"총액":36100}]}
            """.trimIndent()
    }

    @Test
    fun `TOP과 BOTTOM 카테고리 조합 최저가 조회`() {
        val result = mockMvc.perform(get("/api/v1/products/topbottom?category=상의"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        result shouldBe """
                {"카테고리":"상의","최저가":[{"id":17,"브랜드":"C","가격":10000}],"최고가":[{"id":65,"브랜드":"I","가격":11400}]}
            """.trimIndent()
    }

    @Test
    fun `존재하지 않는 카테고리로 조회시 404 응답`() {
        mockMvc.perform(get("/api/v1/products/topbottom?category=INVALID"))
            .andExpect(status().isNotFound)
    }
}