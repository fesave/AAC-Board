package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.architectcoders.aacboard.apptestshare.CoroutinesTestRule
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.use_case.cell.get.GetCellUseCase
import com.architectcoders.aacboard.domain.use_case.cell.save.SaveCellUseCase
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel.EditBoardCellUiState
import com.architectcoders.aacboard.ui.model.PictogramUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EditBoardCellViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    val stateHandle: SavedStateHandle =
        SavedStateHandle(mapOf("dashboardId" to 1, "row" to 2, "column" to 3))

    @Mock
    lateinit var saveCellUseCase: SaveCellUseCase

    @Mock
    lateinit var getCellUseCase: GetCellUseCase

    lateinit var viewModel: EditBoardCellViewModel

    val sampleCellPictogram = CellPictogram("test", "httptest")


    @Test
    fun `When cell is not stored locally, then a null value is defined in the UiState`() =
        runTest {
            getCellUseCase.stub {
                onBlocking { getCellUseCase(1, 2, 3) }.doReturn(null)
            }

            viewModel = EditBoardCellViewModel(stateHandle, saveCellUseCase, getCellUseCase)

            viewModel.state.test {
                assertEquals(EditBoardCellUiState(), awaitItem())
                assertEquals(
                    EditBoardCellUiState().copy(row = 2, column = 3),
                    awaitItem()
                )
                cancel()
            }
        }

    @Test
    fun `When cell is stored locally, then the local content is included in the UiState`() =
        runTest {
            getCellUseCase.stub {
                onBlocking { getCellUseCase(1, 2, 3) }.doReturn(Cell(2, 3, sampleCellPictogram))
            }

            viewModel = EditBoardCellViewModel(stateHandle, saveCellUseCase, getCellUseCase)

            viewModel.state.test {
                assertEquals(EditBoardCellUiState(), awaitItem())
                assertEquals(
                    EditBoardCellUiState().copy(
                        row = 2,
                        column = 3,
                        PictogramUI("test", "httptest")
                    ),
                    awaitItem()
                )
                cancel()
            }
        }

    @Test
    fun `When the cell pictogram is updated, then the pictogram is also updated in the UiState`() =
        runTest {
            getCellUseCase.stub {
                onBlocking { getCellUseCase(1, 2, 3) }.doReturn(Cell(2, 3, sampleCellPictogram))
            }

            viewModel = EditBoardCellViewModel(stateHandle, saveCellUseCase, getCellUseCase)

            viewModel.state.test {
                assertEquals(EditBoardCellUiState(), awaitItem())
                assertEquals(
                    EditBoardCellUiState().copy(
                        row = 2,
                        column = 3,
                        PictogramUI("test", "httptest")
                    ),
                    awaitItem()
                )
                viewModel.onUpdatePictogram(PictogramUI("new", "httpnew"))
                assertEquals(
                    EditBoardCellUiState().copy(row = 2, column = 3, PictogramUI("new", "httpnew")),
                    awaitItem()
                )
                cancel()
            }
        }

    @Test
    fun `When cell is stored locally and  its pictogram is updated, then saveCellUseCase is invoked`() =
        runTest {
            getCellUseCase.stub {
                onBlocking { getCellUseCase(1, 2, 3) }.doReturn(Cell(2, 3, sampleCellPictogram))
            }

            viewModel = EditBoardCellViewModel(stateHandle, saveCellUseCase, getCellUseCase)

            viewModel.state.test {
                assertEquals(EditBoardCellUiState(), awaitItem())
                assertEquals(
                    EditBoardCellUiState().copy(
                        row = 2,
                        column = 3,
                        PictogramUI("test", "httptest")
                    ),
                    awaitItem()
                )
                viewModel.onUpdatePictogram(PictogramUI("new", "httpnew"))
                assertEquals(
                    EditBoardCellUiState().copy(row = 2, column = 3, PictogramUI("new", "httpnew")),
                    awaitItem()
                )
                viewModel.onSaveClicked("final")
                assertTrue(awaitItem().exit)
                verify(saveCellUseCase).invoke(1, Cell(2, 3, CellPictogram("final", "httpnew")))
                cancel()
            }
        }
}