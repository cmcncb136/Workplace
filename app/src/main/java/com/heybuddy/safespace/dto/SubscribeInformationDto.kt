package com.heybuddy.safespace.dto

import java.time.LocalDate

data class SubscribeInformationDto (
    val id: Int,
    val uid: String,
    val name: String,
    val providerId: String,
    val endDate: LocalDate,
    val nextPaymentDate: LocalDate,
    val paymentDate: LocalDate,
    val startDate: LocalDate
)