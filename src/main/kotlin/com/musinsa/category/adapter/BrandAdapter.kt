package com.musinsa.category.adapter

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.mapper.toDomain
import com.musinsa.category.persistence.repository.BrandRepository
import com.musinsa.category.persistence.entity.BrandJpaEntity
import org.springframework.stereotype.Service

@Service
class BrandAdapter(
    private val brandRepository: BrandRepository
) {
    fun findAll(): List<Brand> {
        return brandRepository.findAll().map(BrandJpaEntity::toDomain)
    }

    fun create(brand: String): Brand {
        return brandRepository.save(BrandJpaEntity(name = brand)).toDomain()
    }

    fun findOrCreate(brand: String): Brand {
        return brandRepository.findByName(brand)?.toDomain() ?: this.create(brand)
    }

    fun findByName(brand: String): Brand? {
        return brandRepository.findByName(brand)?.toDomain()
    }
}