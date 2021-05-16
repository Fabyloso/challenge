package com.fabyloso.challenge.di

import com.fabyloso.core.data.common.AppExecutors
import com.fabyloso.guide_list.data.common.GuideListRepository
import com.fabyloso.guide_list.data.remote.GuideRemoteController
import com.fabyloso.guide_list.data.remote.GuideService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideGuideRemoteController() = GuideRemoteController()

    @Provides
    fun provideGuideService(guideRemoteController: GuideRemoteController): GuideService =
        guideRemoteController.liveDataRetrofit.create(GuideService::class.java)

    @Singleton
    @Provides
    fun provideGuideRepository(guideService: GuideService, appExecutors: AppExecutors) =
        GuideListRepository(guideService, appExecutors)

    @Provides
    @Singleton
    fun provideAppExecutors() = AppExecutors()
}