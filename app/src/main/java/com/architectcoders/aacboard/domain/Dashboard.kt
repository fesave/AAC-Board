package com.architectcoders.aacboard.domain

data class Dashboard(
    val id: Int,
    val name: String,
    val rows: Int,
    val columns: Int,
    val cells: List<DashboardCell>
)
