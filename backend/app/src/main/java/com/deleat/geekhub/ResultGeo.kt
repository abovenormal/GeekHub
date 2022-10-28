package com.deleat.geekhub

data class ResultGeo (
    val status : String,
    val meta : Meta_data,
    val addresses: List<Addresses_info>,
    val errorMessage : String = ""
    )

data class Meta_data(
    val totalCount : Int,
    val page : Int,
    val count : Int
)

data class Addresses_info(
    val roadAddress : String,
    val jibunAddress : String,
    val englishAddress : String,
    val x : String,
    val y : String,
    val distance : Double
)