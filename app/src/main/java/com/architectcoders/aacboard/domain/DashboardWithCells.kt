package com.architectcoders.aacboard.domain

data class DashboardWithCells(
    val id: Int,
    val name: String,
    val rows: Int,
    val columns: Int,
    val cells: List<Cell>
)