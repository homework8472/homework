package com.musinsa.category.mapper

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.persistence.entity.BrandJpaEntity

object BrandMapper {
    fun toDomain(entity: BrandJpaEntity): Brand {
        return Brand(
            id = entity.id,
            name = entity.name
        )
    }

    fun toJpaEntity(domain: Brand): BrandJpaEntity {
        return BrandJpaEntity(
            id = domain.id,
            name = domain.name
        )
    }
}

fun Brand.toEntity(): BrandJpaEntity = BrandMapper.toJpaEntity(this)
fun BrandJpaEntity.toDomain(): Brand = BrandMapper.toDomain(this)