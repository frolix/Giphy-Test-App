package com.example.giphytestapp.di

import com.example.data.repository.pagingSourceImpl.GiphyRemotePagingSourceImpl
import com.example.domain.repository.GiphyRepository
import com.example.domain.usecase.GiphyUseCase
import com.example.domain.usecase.GiphyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    internal abstract fun bindGiphyUseCase(useCaseImpl: GiphyUseCaseImpl): GiphyUseCase

    @Binds
    abstract fun bindGiphyRepository(
        giphyRemotePagingSourceImpl: GiphyRemotePagingSourceImpl
    ): GiphyRepository


}