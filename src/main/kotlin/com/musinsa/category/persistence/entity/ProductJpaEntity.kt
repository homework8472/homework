package com.musinsa.category.persistence.entity

import jakarta.persistence.*

@Entity(name = "products")
class ProductJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    val category: CategoryJpaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    val brand: BrandJpaEntity,
    val price: Long,
)