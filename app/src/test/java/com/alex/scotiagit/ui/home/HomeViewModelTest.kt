package com.alex.scotiagit.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alex.scotiagit.data.repositories.GitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()

    private val repository: GitRepository = mockk(relaxed = true)
    private lateinit var viewModel: HomeViewModel
    private var testUserId = "octocat"
    private var testUserIdEmpty = ""


    @Before
    fun setupTest() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `If view model loadGitInfo calls repository's getUserInfo and getRepositories methods`() {
        testDispatcher.run {
            //given
            coEvery { repository.getUserInfo(testUserId) } returns mockk(relaxed = true)
            coEvery { repository.getRepositories(testUserId) } returns mockk(relaxed = true)

            //when
            viewModel.loadGitInfo(testUserId)

            //then
            coVerify { repository.getUserInfo(testUserId) }
            coVerify { repository.getRepositories(testUserId) }
        }
    }

    @Test
    fun `If view model loadGitInfo with empty user calling methods fails`() {
        testDispatcher.run {
            //given
            coEvery { repository.getUserInfo(testUserIdEmpty) } returns mockk(relaxed = true)
            coEvery { repository.getRepositories(testUserIdEmpty) } returns mockk(relaxed = true)

            //when
            viewModel.loadGitInfo(testUserIdEmpty)

            //then
            coVerify(inverse = true) { repository.getUserInfo(testUserIdEmpty) }
            coVerify(inverse = true) { repository.getRepositories(testUserIdEmpty) }
        }
    }

}