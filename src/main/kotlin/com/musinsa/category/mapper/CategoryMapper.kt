package com.musinsa.category.mapper

import com.musinsa.category.domain.model.Category
import com.musinsa.category.persistence.entity.CategoryJpaEntity

object CategoryMapper {
    fun toDomain(entity: CategoryJpaEntity): Category {
        return Category(
            id = entity.id,
            name = entity.name
        )
    }

    fun toJpaEntity(domain: Category): CategoryJpaEntity {
        return CategoryJpaEntity(
            id = domain.id,
            name = domain.name
        )
    }
}

fun Category.toEntity(): CategoryJpaEntity = CategoryMapper.toJpaEntity(this)
fun CategoryJpaEntity.toDomain(): Category = CategoryMapper.toDomain(this)