package com.happilyunmarried.ustraa.Models

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("count")
    var count: Int,
    @SerializedName("products")
    var products: List<Product>
)

data class Product(
    @SerializedName("base_product_name")
    var baseProductName: String,
    @SerializedName("final_price")
    var finalPrice: Int,
    @SerializedName("flavour")
    var flavour: String,
    @SerializedName("has_variants")
    var hasVariants: Boolean,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_urls")
    var imageUrls: ImageUrls,
    @SerializedName("is_fragrance")
    var isFragrance: Boolean,
    @SerializedName("is_in_stock")
    var isInStock: Boolean,
    @SerializedName("name")
    var name: String,
    @SerializedName("no_of_products_in_shipment")
    var noOfProductsInShipment: Int,
    @SerializedName("orig_url")
    var origUrl: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("product_category_content")
    var productCategoryContent: String,
    @SerializedName("product_options")
    var productOptions: List<ProductOption>,
    @SerializedName("rating")
    var rating: Double,
    @SerializedName("review_count")
    var reviewCount: Int,
    @SerializedName("sku")
    var sku: String,
    @SerializedName("tagline")
    var tagline: String,
    @SerializedName("type_id")
    var typeId: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("weight_unit")
    var weightUnit: String
)

data class ImageUrls(
    @SerializedName("x120")
    var x120: String,
    @SerializedName("x15")
    var x15: String,
    @SerializedName("x200")
    var x200: String,
    @SerializedName("x240")
    var x240: String,
    @SerializedName("x300")
    var x300: String,
    @SerializedName("x408")
    var x408: String,
    @SerializedName("x520")
    var x520: String,
    @SerializedName("x60")
    var x60: String
)

data class ProductOption(
    @SerializedName("option_id")
    var optionId: Int,
    @SerializedName("option_qty")
    var optionQty: Int,
    @SerializedName("option_selections")
    var optionSelections: List<Int>
)