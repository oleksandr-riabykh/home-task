package com.alex.scotiagit.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}