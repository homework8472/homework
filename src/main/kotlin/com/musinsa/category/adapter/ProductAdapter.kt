package com.musinsa.category.adapter

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.domain.model.Category
import com.musinsa.category.domain.model.type.OrderType
import com.musinsa.category.domain.model.Product
import com.musinsa.category.mapper.toDomain
import com.musinsa.category.mapper.toEntity
import com.musinsa.category.persistence.repository.ProductRepository
import com.musinsa.category.persistence.entity.ProductJpaEntity
import org.springframework.stereotype.Service

@Service
class ProductAdapter(
    private val productRepository: ProductRepository
) {
    fun findById(productId: Long): Product? {
        return productRepository.findById(productId).map(ProductJpaEntity::toDomain).orElse(null)
    }

    fun findCheapestProducts(categoryId: Long): Product? {
        return productRepository.findTopByCategoryIdOrderByPriceAsc(categoryId)?.toDomain()
    }

    fun findAllProducts(brand: Brand): List<Product> {
        return productRepository.findAllByBrand(brand.toEntity()).map(ProductJpaEntity::toDomain)
    }

    fun findAllTopOrderByPrice(categoryId: Long, orderType: OrderType): List<Product> {
        return when (orderType) {
            OrderType.ASC -> productRepository.findTopByCategoryIdOrderByPriceAsc(categoryId)?.let {
                productRepository.findAllByCategoryIdAndPriceOrderByPriceAsc(categoryId, it.price).map(ProductJpaEntity::toDomain)
            } ?: emptyList()

            OrderType.DESC -> productRepository.findTopByCategoryIdOrderByPriceDesc(categoryId)?.let {
                productRepository.findAllByCategoryIdAndPriceOrderByPriceDesc(categoryId, it.price).map(ProductJpaEntity::toDomain)
            } ?: emptyList()
        }
    }

    fun create(category: Category, brand: Brand, price: Long): Product {
        val entity = ProductJpaEntity(
            category = category.toEntity(),
            brand = brand.toEntity(),
            price = price
        )

        return productRepository.save(entity).toDomain()
    }

    fun update(productId: Long, category: Category, brand: Brand, price: Long): Product {
        val entity = ProductJpaEntity(
            id = productId,
            category = category.toEntity(),
            brand = brand.toEntity(),
            price = price
        )

        return productRepository.save(entity).toDomain()
    }

    fun delete(productId: Long) {
        return productRepository.deleteById(productId)
    }

}
