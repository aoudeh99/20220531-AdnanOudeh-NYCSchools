package com.example.nyc_schools_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.model.remote.Repository
import com.example.nyc_schools_test.model.remote.SchoolListResponse
import com.example.nyc_schools_test.viewmodel.NYCViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.nio.file.Files.list

class NYCViewModelTest {

    @get:Rule var rule = InstantTaskExecutorRule()
    lateinit var subject:NYCViewModel
    lateinit var repository: Repository
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScopeCoroutine = TestScope(testDispatcher)

    @Before
    fun setup(){
        repository = mockk()
        subject = NYCViewModel(repository,testScopeCoroutine)

    }

    @Test
    fun getSchoolList_Success(){
        every { repository.NYCSchoolCatched() } returns flowOf(StateAction.SUCCESS(listOf(mockk<SchoolListResponse>())))

        var StateActionTestList = mutableListOf<StateAction>()
        subject.schoolResponse.observeForever{
            StateActionTestList.add(it)
        }

        subject.getSchoolList()
        assertEquals(StateActionTestList.size, 1)

        val successTest = StateActionTestList.get(0) as StateAction.SUCCESS<List<SchoolListResponse>>

        assertEquals(successTest.response.size, 1)

    }
    

}
