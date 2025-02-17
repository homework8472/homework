package com.musinsa.category.persistence.repository

import com.musinsa.category.persistence.entity.BrandJpaEntity
import com.musinsa.category.persistence.entity.ProductJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductJpaEntity, Long> {
    fun findAllByBrand(brand: BrandJpaEntity): List<ProductJpaEntity>
    fun findTopByCategoryIdOrderByPriceAsc(categoryId: Long): ProductJpaEntity?
    fun findTopByCategoryIdOrderByPriceDesc(categoryId: Long): ProductJpaEntity?
    fun findAllByCategoryIdAndPriceOrderByPriceAsc(categoryId: Long, price: Long): List<ProductJpaEntity>
    fun findAllByCategoryIdAndPriceOrderByPriceDesc(categoryId: Long, price: Long): List<ProductJpaEntity>
}
