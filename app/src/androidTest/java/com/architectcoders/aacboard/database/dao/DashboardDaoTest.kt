package com.architectcoders.aacboard.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.architectcoders.aacboard.apptestshare.CoroutinesTestRule
import com.architectcoders.aacboard.database.entity.CellEntity
import com.architectcoders.aacboard.testrules.KoinRoomTestRule
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DashboardDaoTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @get:Rule
    val koinRule = KoinRoomTestRule()

    val dashboardDao: DashboardDao by inject(DashboardDao::class.java)

    @Test
    fun givenNoDashboardInDbThenGetDashboardsFlowReturnsNull() = runTest {
        dashboardDao.getDashboards().test {
            val dashboardList = awaitItem()
            assertTrue(dashboardList.isEmpty())
            cancel()
        }
    }

    @Test
    fun givenNoDashboardInDbWhenInsertDashboardThenGetDashboardsFlowUpdated() = runTest {
        dashboardDao.getDashboards().test {
            var dashboardList = awaitItem()
            assertTrue(dashboardList.isEmpty())
            buildAndPopulateDashboard(dashboardDao)
            dashboardList = awaitItem()  //Insert Dashboard
            assertEquals(dashboardList.size, 1)
            assertEquals(dashboardList.get(0).id, 1)
            cancel()
        }
    }

    @Test
    fun givenDashboardInDbWhenDeleteDashboardThenGetDashboardsFlowUpdated() = runTest {

        dashboardDao.getDashboards().test {
            var dashboardList = awaitItem()
            assertTrue(dashboardList.isEmpty())
            buildAndPopulateDashboard(dashboardDao)
            dashboardList = awaitItem()  //Insert Dashboard
            assertEquals(dashboardList.size, 1)
            dashboardDao.deleteDashboardEntity(1)
            dashboardList = awaitItem()
            assertTrue(dashboardList.isEmpty())
            cancel()
        }
    }

    @Test
    fun givenNoDashboardInDbThenGetDashboardFlowReturnsNull() = runTest {
        dashboardDao.getDashboard(1).test {
            val dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            cancel()
        }
    }

    @Test
    fun givenNoDashboardInDbWhenInsertDashboardThennGetDashboardFlowUpdated() = runTest {
        dashboardDao.getDashboard(1).test {
            var dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            buildAndPopulateDashboard(dashboardDao)
            awaitItem()  //Insert Dashboard
            dashboardWithCells = awaitItem()  //Insert Cells
            assertEquals(buildDashboard(), dashboardWithCells?.dashboard)
            assertEquals(2 * 3, dashboardWithCells?.cells?.size)
            assertTrue(dashboardWithCells?.cells?.contains(buildCell(1, 1, 1)) ?: false)
            cancel()
        }
    }

    @Test
    fun givenDashboardinDbWhenCellInsertedThenGetDashboardFlowUpdatedWithCellContent() = runTest {
        dashboardDao.getDashboard(1).test {
            var dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            buildAndPopulateDashboard(dashboardDao)
            awaitItem()  //Insert Dashboard
            dashboardWithCells = awaitItem()  //Insert Cells
            assertEquals(buildDashboard(), dashboardWithCells?.dashboard)
            assertEquals(2 * 3, dashboardWithCells?.cells?.size)
            assertTrue(dashboardWithCells?.cells?.contains(buildCell(1, 1, 1)) ?: false)
            val updatedCell = buildCell(1, 1, 1, "hola", "http")
            dashboardDao.insertCell(updatedCell)
            dashboardWithCells = awaitItem()  //Insert Cell
            assertTrue(dashboardWithCells?.cells?.contains(updatedCell) ?: false)
            dashboardDao.deleteCell(updatedCell)
            cancel()
        }
    }

    @Test
    fun givenDashboardinDbWhenCellDeletedThenGetDashboardFlowUpdatedWithoutCell() = runTest {
        dashboardDao.getDashboard(1).test {
            var dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            buildAndPopulateDashboard(dashboardDao)
            awaitItem()  //Insert Dashboard
            dashboardWithCells = awaitItem()  //Insert Cells
            assertEquals(buildDashboard(), dashboardWithCells?.dashboard)
            assertEquals(2 * 3, dashboardWithCells?.cells?.size)
            assertTrue(dashboardWithCells?.cells?.contains(buildCell(1, 1, 1)) ?: false)
            val deleteCell: CellEntity = buildCell(1, 1, 1, "hola", "http")
            dashboardDao.deleteCell(deleteCell)
            dashboardWithCells = awaitItem()  //Delete Cell
            assertEquals(2 * 3 - 1, dashboardWithCells?.cells?.size)
            val deletedCell = dashboardDao.getCell(1, 1, 1)
            assertNull(deletedCell)
            cancel()
        }
    }

    @Test
    fun givenDashboardinDbWhenDashboardDeletedThenGetDashboardFlowUpdatedWithNullValue() = runTest {
        dashboardDao.getDashboard(1).test {
            var dashboardWithCells = awaitItem()
            assertNull(dashboardWithCells)
            buildAndPopulateDashboard(dashboardDao)
            awaitItem()  //Insert Dashboard
            dashboardWithCells = awaitItem()  //Insert Cells
            assertEquals(buildDashboard(), dashboardWithCells?.dashboard)
            assertEquals(2 * 3, dashboardWithCells?.cells?.size)
            assertTrue(dashboardWithCells?.cells?.contains(buildCell(1, 1, 1)) ?: false)
            dashboardDao.deleteDashboardEntity(1)
            dashboardWithCells = awaitItem()  //Delete Dashboard
            assertNull(dashboardWithCells)
            val deletedCell = dashboardDao.getCell(1, 0, 0)
            assertNull(deletedCell)
            cancel()
        }
    }
}