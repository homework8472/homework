package com.musinsa.category.domain.service

import com.musinsa.category.adapter.BrandAdapter
import com.musinsa.category.domain.model.Brand
import org.springframework.stereotype.Service

@Service
class BrandDomainService(
    private val brandAdapter: BrandAdapter,
) {
    fun findAll(): List<Brand> {
        return brandAdapter.findAll()
    }
}