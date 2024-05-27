package com.heybuddy.safespace.dto

import java.time.LocalDate

data class WorkplaceInformationDto(
    val uid: String,
    val workspaceIp: String?,
    val dancPin: String,
    val workspaceName: String,
    val ownerName: String,
    val address: String,
    val businessType: String,
    val workspacePhone: String,
    val joinDate: String?
)
 