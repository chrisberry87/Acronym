package com.example.acronyms

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.acronyms.repository.AbbItem
import com.example.acronyms.repository.AcromineApi
import com.example.acronyms.repository.Lf
import com.example.acronyms.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AcronymsViewModelTest {

    @Mock
    private lateinit var observerError: Observer<String>
    @Mock
    private lateinit var observerData: Observer<List<AbbItem>>

    @Mock
    lateinit var acromineApi: AcromineApi

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(acromineApi)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel.meaningsData.observeForever(observerData)
        viewModel.errorMessage.observeForever(observerError)
    }

    @Test
    fun `get meaning data Success`() = runTest {
        var data = mutableListOf<AbbItem>()
        var lfList = mutableListOf<Lf>()
        lfList.add(Lf(1, "meaning 1", 1990))
        lfList.add(Lf(2, "meaning 2", 1991))
        lfList.add(Lf(3, "meaning 3", 1992))
        data.add(AbbItem(lfList, "sf"))
        Mockito.`when`(acromineApi.getMeanings("")).thenReturn(Response.success(data))

        viewModel.getMeaningData()

        Mockito.verify(observerData).onChanged(data)
        Mockito.verify(observerError).onChanged("SUCCESS")
    }

    @Test
    fun `get meaning data Fail`() = runTest {
        Mockito.`when`(acromineApi.getMeanings("")).thenReturn(Response.error(404,  "".toResponseBody()))

        viewModel.getMeaningData()

        Mockito.verify(observerError).onChanged("Response not successful")
    }

}