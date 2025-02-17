package com.musinsa.category.domain.service

import com.musinsa.category.adapter.CategoryAdapter
import com.musinsa.category.domain.model.Category
import com.musinsa.category.support.exception.EntityNotFoundError
import org.springframework.stereotype.Service

@Service
class CategoryDomainService(
    private val categoryAdapter: CategoryAdapter,
) {
    fun findAll(): List<Category> {
        return categoryAdapter.findAll()
    }

    fun findByName(categoryName: String): Category {
        return categoryAdapter.findByName(categoryName) ?: throw EntityNotFoundError(message = "$categoryName 카테고리를 찾을 수 없습니다.")
    }
}