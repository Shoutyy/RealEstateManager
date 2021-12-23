package com.example.realestatemanager.model

class PropertyModelProcessed (
    val propertyId: Int,
    val path: String? = "unknown path",
    val type: String,
    val district: String? = "unknown district",
    val price: String
)
