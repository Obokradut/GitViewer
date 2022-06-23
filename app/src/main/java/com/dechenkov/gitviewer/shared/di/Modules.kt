package com.dechenkov.gitviewer.shared.di

import android.content.Context
import com.dechenkov.gitviewer.modules.navigation.MainNavProvider
import com.dechenkov.gitviewer.shared.AppRepository
import com.dechenkov.gitviewer.shared.IAppRepository
import com.dechenkov.gitviewer.shared.IKeyValueStorage
import com.dechenkov.gitviewer.shared.KeyValueStorage
import com.dechenkov.gitviewer.shared.gitApi.IGitApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
@Module
class Modules {
    @Singleton
    @Provides
    fun provideCoreNavProvider(): MainNavProvider = MainNavProvider()

    private val json = Json { ignoreUnknownKeys = true }
    private val mediaType = MediaType.get("application/json; charset=utf-8")
    @Singleton
    @Provides
    fun provideGitApi(): IGitApi
            = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(json.asConverterFactory(mediaType))
        .build()
        .create(IGitApi::class.java)

    @Singleton
    @Provides
    fun provideKeyValueStorage(@ApplicationContext context: Context): IKeyValueStorage = KeyValueStorage(context)

    @Singleton
    @Provides
    fun provideAppRepository(
        gitApi: IGitApi,
        keyValueStorage: IKeyValueStorage
    ): IAppRepository = AppRepository(
        gitApi = gitApi,
        keyValueStorage = keyValueStorage
    )
}