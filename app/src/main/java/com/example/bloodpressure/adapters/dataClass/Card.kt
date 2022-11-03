package com.example.bloodpressure.adapters.dataClass

data class Card (
    val cardDate: String?,
    val cardHealthy: String?,
    val cardUnhealthy: String?,
    val cardSymptoms: String?,
    val cardCare: String?,
    val cardUpper: Int?,
    val cardLower: Int?,
    val cardOther: String?,
    var id: Int
)