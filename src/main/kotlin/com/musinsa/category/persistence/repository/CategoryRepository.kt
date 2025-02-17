package com.musinsa.category.persistence.repository

import com.musinsa.category.persistence.entity.CategoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryJpaEntity, Long> {
    fun findByName(categoryName: String): CategoryJpaEntity?
}
