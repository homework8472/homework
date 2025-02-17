package com.musinsa.category.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.category.presentation.dto.request.CreateProductRequest
import com.musinsa.category.presentation.dto.request.UpdateProductRequest
import com.musinsa.category.domain.service.ProductAdminDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AdminControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var productAdminDomainService: ProductAdminDomainService

    @Test
    fun `상품 생성 API 테스트`() {
        val request = CreateProductRequest(category = "TOP", brand = "A", price = 10000)

        mockMvc.perform(
            post("/api/v1/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.category.name").value("TOP"))
            .andExpect(jsonPath("$.brand.name").value("A"))
            .andExpect(jsonPath("$.price").value(10000))
    }

    @Test
    fun `상품 수정 API 테스트`() {
        val createdProduct = productAdminDomainService.create(
            CreateProductRequest("TOP", "A", 10000)
        )

        val request = UpdateProductRequest(category = "TOP", brand = "A", price = 20000)

        mockMvc.perform(
            put("/api/v1/admin/products/${createdProduct.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.category.name").value("TOP"))
            .andExpect(jsonPath("$.brand.name").value("A"))
            .andExpect(jsonPath("$.price").value(20000))
    }

    @Test
    fun `존재하지 않는 상품 수정시 404 응답`() {
        val request = UpdateProductRequest("TOP", "A", 20000)

        mockMvc.perform(
            put("/api/v1/admin/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `상품 삭제 API 테스트`() {
        val createdProduct = productAdminDomainService.create(
            CreateProductRequest("TOP", "A", 10000)
        )

        mockMvc.perform(
            delete("/api/v1/admin/products/${createdProduct.id}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.category.name").value("TOP"))
            .andExpect(jsonPath("$.brand.name").value("A"))
            .andExpect(jsonPath("$.price").value(10000))
    }

    @Test
    fun `존재하지 않는 상품 삭제시 404 응답`() {
        mockMvc.perform(
            delete("/api/v1/admin/products/999")
        )
            .andExpect(status().isNotFound)
    }
}