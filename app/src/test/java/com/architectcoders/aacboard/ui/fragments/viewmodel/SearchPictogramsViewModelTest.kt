package com.architectcoders.aacboard.ui.fragments.viewmodel

import app.cash.turbine.test
import com.architectcoders.aacboard.apptestshare.CoroutinesTestRule
import com.architectcoders.aacboard.domain.data.Error
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.Response.Failure
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.use_case.location.GetUserLanguageUseCase
import com.architectcoders.aacboard.domain.use_case.search.SearchPictogramsUseCase
import com.architectcoders.aacboard.ui.fragments.viewmodel.SearchPictogramsViewModel.SearchPictogramUiState
import com.architectcoders.aacboard.ui.model.PictogramUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchPictogramsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var searchPictogramsUseCase: SearchPictogramsUseCase

    @Mock
    lateinit var getUserLanguageUseCase: GetUserLanguageUseCase

    private lateinit var viewModel: SearchPictogramsViewModel

    private val sampleCellPictogram = CellPictogram("tres_ui", "http3")
    private val samplePictogramUi = PictogramUI("tres_ui", "http3")

    @Before
    fun setUp() {
        getUserLanguageUseCase.stub {
            onBlocking { getUserLanguageUseCase() }.doReturn("es")
        }
        searchPictogramsUseCase.stub {
            onBlocking { searchPictogramsUseCase(any(), eq("tres")) }.doReturn(
                Response.Success(
                    listOf(sampleCellPictogram)
                )
            )
        }

        searchPictogramsUseCase.stub {
            onBlocking {
                searchPictogramsUseCase(
                    any(),
                    eq("tress")
                )
            }.doReturn(Failure(Error.NoMatchFound))
        }

        searchPictogramsUseCase.stub {
            onBlocking {
                searchPictogramsUseCase(
                    any(),
                    eq("serverError")
                )
            }.doReturn(Failure(Error.Server(400)))
        }
        viewModel = SearchPictogramsViewModel(searchPictogramsUseCase, getUserLanguageUseCase)
    }

    @Test
    fun `When searchPictogramsUseCase returns a pictogram list, then UiState is updated with the list of pictograms`() =
        runTest {
            viewModel.state.test {
                assertEquals(SearchPictogramUiState(), awaitItem())
                viewModel.onSearchClicked("tres")
                assertEquals(
                    SearchPictogramUiState().copy(loading = true, searchString = "tres"),
                    awaitItem()
                )
                assertEquals(
                    SearchPictogramUiState().copy(
                        searchString = "tres", foundPictograms = listOf(
                            samplePictogramUi
                        )
                    ), awaitItem()
                )
                cancel()
            }
        }

    @Test
    fun `When searchPictogramsUseCase returns NoMatchFoundError, then NoMatchFound error is shown in UiState`() =
        runTest {
            viewModel.state.test {
                assertEquals(SearchPictogramUiState(), awaitItem())
                viewModel.onSearchClicked("tress")
                assertEquals(
                    SearchPictogramUiState().copy(loading = true, searchString = "tress"),
                    awaitItem()
                )
                assertEquals(
                    SearchPictogramUiState().copy(
                        searchString = "tress",
                        error = Error.NoMatchFound
                    ), awaitItem()
                )
                cancel()
            }
        }

    @Test
    fun `When searchPictogramsUseCase returns ServerError, then Server error is shown in UiState`() =
        runTest {
            viewModel.state.test {
                assertEquals(SearchPictogramUiState(), awaitItem())
                viewModel.onSearchClicked("serverError")
                assertEquals(
                    SearchPictogramUiState().copy(
                        loading = true,
                        searchString = "serverError"
                    ), awaitItem()
                )
                val tempError = awaitItem().error
                assertTrue(tempError is Error.Server && tempError.code == 400)
                cancel()
            }
        }

    @Test
    fun `When onPictogramClicked is called on one pictogram, then the selected pictogram is updated in UiState`() =
        runTest {
            viewModel.state.test {
                assertEquals(SearchPictogramUiState(), awaitItem())
                viewModel.onSearchClicked("tres")
                assertEquals(
                    SearchPictogramUiState().copy(loading = true, searchString = "tres"),
                    awaitItem()
                )
                assertEquals(
                    SearchPictogramUiState().copy(
                        searchString = "tres", foundPictograms = listOf(
                            samplePictogramUi
                        )
                    ), awaitItem()
                )
                viewModel.onPictogramClicked(PictogramUI("tres_ui", "http3"))
                assertEquals(
                    SearchPictogramUiState().copy(
                        searchString = "tres", foundPictograms = listOf(
                            samplePictogramUi
                        ), selectedPictogram = samplePictogramUi.copy(keyword = "tres")
                    ), awaitItem()
                )
                cancel()
            }
        }
}