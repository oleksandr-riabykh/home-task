package com.alex.scotiagit.data.repositories

import com.alex.scotiagit.data.model.Repo
import com.alex.scotiagit.data.model.User
import com.alex.scotiagit.data.network.GitService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GitRepository @Inject constructor(
    private val gitService: GitService,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun getRepositories(userId: String): List<Repo>? = withContext(defaultDispatcher) {
        return@withContext gitService.getRepositories(userId)
    }

    suspend fun getUserInfo(userId: String): User = withContext(defaultDispatcher) {
        return@withContext gitService.getUserInfo(userId)
    }

}