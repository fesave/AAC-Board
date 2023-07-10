package com.architectcoders.aacboard.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.architectcoders.aacboard.apptestshare.CoroutinesTestRule
import com.architectcoders.aacboard.database.dao.DashboardDao
import com.architectcoders.aacboard.testrules.KoinRoomTestRule
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DashboardLocalDataSourceImplTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @get:Rule
    val koinRule = KoinRoomTestRule()

    val dashboardDao: DashboardDao by inject(DashboardDao::class.java)
    lateinit var localDataSource: DashboardLocalDataSourceImpl

    @Before
    fun setUp() {
        localDataSource = DashboardLocalDataSourceImpl(dashboardDao)
    }

    @Test
    fun whenSaveDashboardThenDashboardAndCellsStoredInLocalStorage() = runTest {
        localDataSource.getDashBoardWithCells(1).test {
            var dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            val newDashboardWithCells = buildDashboardWithCells()
            localDataSource.saveDashboard(newDashboardWithCells)
            awaitItem() //Insert Dashboard
            dashboardWithCells = awaitItem() //Insert Cells
            assertEquals(newDashboardWithCells, dashboardWithCells)
            cancel()
        }
    }

    @Test
    fun givenDashboardInDbwhenCellInsertedThenGetDashboardWithCellsFlowUpdated() = runTest {
        localDataSource.getDashBoardWithCells(1).test {
            var dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            val newDashboardWithCells = buildDashboardWithCells()
            localDataSource.saveDashboard(newDashboardWithCells)
            awaitItem() //Insert Dashboard
            dashboardWithCells = awaitItem() //Insert Cells
            assertEquals(newDashboardWithCells, dashboardWithCells)
            val newCell = buildCell(1, 1, "changed keyword", "changed url")
            localDataSource.saveDashboardCell(1, newCell)
            dashboardWithCells = awaitItem() //Insert Cell
            assertEquals(
                newCell,
                dashboardWithCells?.cells?.first { it.row == 1 && it.column == 1 })
            cancel()
        }
    }

}