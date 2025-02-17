package com.musinsa.category.mapper

import com.musinsa.category.domain.model.Product
import com.musinsa.category.persistence.entity.ProductJpaEntity

object ProductMapper {
    fun toDomain(entity: ProductJpaEntity): Product {
        return Product(
            id = entity.id,
            category = entity.category.toDomain(),
            brand = entity.brand.toDomain(),
            price = entity.price
        )
    }

    fun toJpaEntity(domain: Product): ProductJpaEntity {
        return ProductJpaEntity(
            id = domain.id,
            category = domain.category.toEntity(),
            brand = domain.brand.toEntity(),
            price = domain.price
        )
    }
}

fun Product.toEntity(): ProductJpaEntity = ProductMapper.toJpaEntity(this)
fun ProductJpaEntity.toDomain(): Product = ProductMapper.toDomain(this)
