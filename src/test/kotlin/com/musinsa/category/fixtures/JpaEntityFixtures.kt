package com.musinsa.category.fixtures

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.mapper.toEntity
import com.musinsa.category.persistence.entity.BrandJpaEntity
import com.musinsa.category.persistence.entity.ProductJpaEntity

val category = categories.first()
val brand = Brand(1, "A")
val price = 10000L

val productJpaEntity = ProductJpaEntity(
    category = category.toEntity(),
    brand = brand.toEntity(),
    price = price
)

val brandJpaEntities = listOf(
    BrandJpaEntity(id = 1, name = "A"),
    BrandJpaEntity(id = 2, name = "B")
)