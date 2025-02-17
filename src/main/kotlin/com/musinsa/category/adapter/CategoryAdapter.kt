package com.musinsa.category.adapter

import com.musinsa.category.domain.model.Category
import com.musinsa.category.mapper.toDomain
import com.musinsa.category.persistence.repository.CategoryRepository
import com.musinsa.category.persistence.entity.CategoryJpaEntity
import org.springframework.stereotype.Service

@Service
class CategoryAdapter(
    private val categoryRepository: CategoryRepository
) {
    fun findAll(): List<Category> {
        return categoryRepository.findAll().map(CategoryJpaEntity::toDomain)
    }

    fun findByName(name: String): Category? {
        return categoryRepository.findByName(name)?.toDomain()
    }

    fun create(name: String): Category {
        return categoryRepository.save(CategoryJpaEntity(name = name)).toDomain()
    }

    fun findOrCreate(category: String): Category {
        return categoryRepository.findByName(category)?.toDomain() ?: create(category)
    }
}