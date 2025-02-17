package com.musinsa.category.fixtures

import com.musinsa.category.domain.model.Brand
import com.musinsa.category.domain.model.BrandProduct
import com.musinsa.category.domain.model.Product
import com.musinsa.category.domain.model.Category
import com.musinsa.category.presentation.dto.response.CheapestBrandResponse
import com.musinsa.category.presentation.dto.response.CheapestCategoryResponse
import com.musinsa.category.presentation.dto.response.TopBottomPriceResponse

val categories = listOf(
    Category(1, "상의"),
    Category(2, "아우터"),
    Category(3, "바지"),
    Category(4, "스니커즈"),
    Category(5, "가방"),
    Category(6, "모자"),
    Category(7, "양말"),
    Category(8, "악세서리")
)

val brands = listOf(
    Brand(1, "A"), Brand(2, "B"), Brand(3, "C"),
    Brand(4, "D"), Brand(5, "E"), Brand(6, "F"),
    Brand(7, "G"), Brand(8, "H"), Brand(9, "I")
)

val products = listOf(
    // Brand A
    Product(1, categories[0], brands[0], 11200),
    Product(2, categories[1], brands[0], 5500),
    Product(3, categories[2], brands[0], 4200),
    Product(4, categories[3], brands[0], 9000),
    Product(5, categories[4], brands[0], 2000),
    Product(6, categories[5], brands[0], 1700),
    Product(7, categories[6], brands[0], 1800),
    Product(8, categories[7], brands[0], 2300),

    // Brand B
    Product(9, categories[0], brands[1], 10500),
    Product(10, categories[1], brands[1], 5900),
    Product(11, categories[2], brands[1], 3800),
    Product(12, categories[3], brands[1], 9100),
    Product(13, categories[4], brands[1], 2100),
    Product(14, categories[5], brands[1], 2000),
    Product(15, categories[6], brands[1], 2000),
    Product(16, categories[7], brands[1], 2200),

    // Brand C
    Product(17, categories[0], brands[2], 10000),
    Product(18, categories[1], brands[2], 6200),
    Product(19, categories[2], brands[2], 3300),
    Product(20, categories[3], brands[2], 9200),
    Product(21, categories[4], brands[2], 2200),
    Product(22, categories[5], brands[2], 1900),
    Product(23, categories[6], brands[2], 2200),
    Product(24, categories[7], brands[2], 2100),

    // Brand D
    Product(25, categories[0], brands[3], 10100),
    Product(26, categories[1], brands[3], 5100),
    Product(27, categories[2], brands[3], 3000),
    Product(28, categories[3], brands[3], 9500),
    Product(29, categories[4], brands[3], 2500),
    Product(30, categories[5], brands[3], 1500),
    Product(31, categories[6], brands[3], 2400),
    Product(32, categories[7], brands[3], 2000),

    // Brand E
    Product(33, categories[0], brands[4], 10700),
    Product(34, categories[1], brands[4], 5000),
    Product(35, categories[2], brands[4], 3800),
    Product(36, categories[3], brands[4], 9900),
    Product(37, categories[4], brands[4], 2300),
    Product(38, categories[5], brands[4], 1800),
    Product(39, categories[6], brands[4], 2100),
    Product(40, categories[7], brands[4], 2100),

    // Brand F
    Product(41, categories[0], brands[5], 11200),
    Product(42, categories[1], brands[5], 7200),
    Product(43, categories[2], brands[5], 4000),
    Product(44, categories[3], brands[5], 9300),
    Product(45, categories[4], brands[5], 2100),
    Product(46, categories[5], brands[5], 1600),
    Product(47, categories[6], brands[5], 2300),
    Product(48, categories[7], brands[5], 1900),

    // Brand G
    Product(49, categories[0], brands[6], 10500),
    Product(50, categories[1], brands[6], 5800),
    Product(51, categories[2], brands[6], 3900),
    Product(52, categories[3], brands[6], 9000),
    Product(53, categories[4], brands[6], 2200),
    Product(54, categories[5], brands[6], 1700),
    Product(55, categories[6], brands[6], 2100),
    Product(56, categories[7], brands[6], 2000),

    // Brand H
    Product(57, categories[0], brands[7], 10800),
    Product(58, categories[1], brands[7], 6300),
    Product(59, categories[2], brands[7], 3100),
    Product(60, categories[3], brands[7], 9700),
    Product(61, categories[4], brands[7], 2100),
    Product(62, categories[5], brands[7], 1600),
    Product(63, categories[6], brands[7], 2000),
    Product(64, categories[7], brands[7], 2000),

    // Brand I
    Product(65, categories[0], brands[8], 11400),
    Product(66, categories[1], brands[8], 6700),
    Product(67, categories[2], brands[8], 3200),
    Product(68, categories[3], brands[8], 9500),
    Product(69, categories[4], brands[8], 2400),
    Product(70, categories[5], brands[8], 1700),
    Product(71, categories[6], brands[8], 1700),
    Product(72, categories[7], brands[8], 2100)
)

val brandProducts = brands.map { brand ->
    BrandProduct(brand, products.filter { it.brand == brand })
}

val productsByBrandD = products.filter { it.brand.name == "D" }

val productTopC = products.filter { it.brand.name == "C" }.maxBy { it.price }

val cheapestCategoryProducts = categories.map {
    products.filter { p -> p.category.id == it.id }.minBy { it.price }
}

val cheapestCategoryResponse = CheapestCategoryResponse(cheapestCategoryProducts)

val cheapestBrandResponse = CheapestBrandResponse.of(listOf(BrandProduct(Brand(4, "D"), productsByBrandD)))

val lower = products.filter { it.brand.name == "I" }.minBy { it.price }
val lower2 = products.filter { it.brand.name == "I" }.minBy { it.price }.copy(brand = brands[0])
val higher = products.filter { it.brand.name == "C" }.maxBy { it.price }

val topBottomPriceResponse = TopBottomPriceResponse(categories[0], listOf(lower), listOf(higher))
val top1Bottom1SameResponse = TopBottomPriceResponse(categories[0], listOf(lower), listOf(lower))


fun List<Product>.first(category: Category) = this.first { it.category == category }