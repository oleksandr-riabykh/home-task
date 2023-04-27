package com.alex.scotiagit.data.repository

import com.alex.scotiagit.data.network.GitService
import com.alex.scotiagit.data.repositories.GitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GitRepositoryTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private val networkService: GitService = mockk(relaxed = true)
    private lateinit var gitRepo: GitRepository
    private var testUserId = "octocat"

    @Before
    fun setupTest() {
        gitRepo = GitRepository(networkService, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `If getUserInfo then get user response from service`() {
        runTest {
            //given
            coEvery { networkService.getUserInfo(testUserId) } returns mockk(relaxed = true)

            //when
            gitRepo.getUserInfo(testUserId)

            //then
            coVerify { networkService.getUserInfo(testUserId) }
        }
    }

    @Test
    fun `If getRepositories then get repo response from service`() {
        runTest {
            //given
            coEvery { networkService.getRepositories(testUserId) } returns mockk(relaxed = true)

            //when
            gitRepo.getRepositories(testUserId)

            //then
            coVerify { networkService.getRepositories(testUserId) }
        }
    }
}