package com.example.domain.di

import com.example.domain.usecase.GiphyUseCase
import com.example.domain.usecase.GiphyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {
    @Binds
    @Singleton
    internal abstract fun bindGiphyUseCase(useCaseImpl: GiphyUseCaseImpl): GiphyUseCase


}