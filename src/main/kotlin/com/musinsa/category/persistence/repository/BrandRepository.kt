package com.musinsa.category.persistence.repository

import com.musinsa.category.persistence.entity.BrandJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<BrandJpaEntity, Long> {
    fun findByName(brand: String): BrandJpaEntity?
}
