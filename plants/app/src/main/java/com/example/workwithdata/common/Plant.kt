package com.example.workwithdata.common

import java.util.*

data class Plant(
    val id: Long,
    val productName: String,
    val productFamily: String,
    val productScientificName: String,
    val availability: Boolean,
    val price: String,
    val color: String,
    val image: String?,
    val description: String,
    val exportedFrom: String,
    val exportDate: String,
    val exportTime: String,
    val exportTimeZone: String,
    val exporterCompanyName: String,
    val exporterCompanyImage: String,
    val descendants: List<Descendant>,


    var priceNumeric: Double?,
    var realDate: Long?,
    var childCount: Int?

)
