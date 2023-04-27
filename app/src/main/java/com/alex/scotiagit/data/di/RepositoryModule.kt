package com.alex.scotiagit.data.di

import com.alex.scotiagit.data.network.GitService
import com.alex.scotiagit.data.repositories.GitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesGitRepository(service: GitService, dispatcher: CoroutineDispatcher): GitRepository {
        return GitRepository(service, dispatcher)
    }
}