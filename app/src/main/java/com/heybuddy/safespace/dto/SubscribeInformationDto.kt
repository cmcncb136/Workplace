package com.heybuddy.safespace.dto

import java.time.LocalDate

data class SubscribeInformationDto (
    val id: Int,
    val uid: String,
    val name: String,
    val provider: ProviderDto,
    val product: ProductDto,
    val endDate: String,
    val nextPaymentDate: String,
    val paymentDate: String,
    val startDate: String
)