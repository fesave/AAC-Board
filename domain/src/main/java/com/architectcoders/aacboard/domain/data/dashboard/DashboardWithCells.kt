package com.architectcoders.aacboard.domain.data.dashboard

import com.architectcoders.aacboard.domain.data.cell.Cell

data class DashboardWithCells(
    val id: Int,
    val name: String,
    val rows: Int,
    val columns: Int,
    val cells: List<Cell>
)