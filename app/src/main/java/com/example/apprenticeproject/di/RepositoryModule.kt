package com.example.apprenticeproject.di

import com.example.apprenticeproject.network.HiringApi
import com.example.apprenticeproject.ui.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        hiringApi: HiringApi,
    ): HomeRepository {
        return HomeRepository(hiringApi)
    }
}
