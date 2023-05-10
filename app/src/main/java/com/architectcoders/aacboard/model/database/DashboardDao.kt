package com.architectcoders.aacboard.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardDao {

    @Query("SELECT * FROM DashboardEntity")
    fun getDashboards(): Flow<List<DashboardEntity>>

    @Query("SELECT * FROM DashboardEntity where id = :id")
    fun getDashboard(id: Int): Flow<DashboardEntityWithCellEntities>

    @Query("SELECT * FROM CellEntity where " +
            "dashboardId = :dashboardId AND row = :row AND column = :column LIMIT 1")
    suspend fun getCell(dashboardId: Int, row: Int, column: Int): CellEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboard(dashboard: DashboardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCells(cells: List<CellEntity>)

    @Query("DELETE FROM DashboardEntity WHERE id = :id")
    suspend fun deleteDashboardEntity(id: Int)

    @Delete
    suspend fun deleteCells(cell: List<CellEntity>)

}