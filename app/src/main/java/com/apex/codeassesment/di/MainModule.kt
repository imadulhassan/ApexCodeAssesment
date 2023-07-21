package com.apex.codeassesment.di

import android.content.Context
import android.content.SharedPreferences
import com.apex.codeassesment.repo.UserRepository
import com.apex.codeassesment.repo.UserRepositoryImpl
import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.data.remote.UserApis
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

     val BASE_URL="https://randomuser.me/"
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("random-user-preferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun providePreferencesManager(): PreferencesManager = PreferencesManager()

    @Provides
    fun provideLocalDataSource(
        preferencesManager: PreferencesManager,
        moshi: Moshi
    ): LocalDataSource {
        return LocalDataSource(preferencesManager, moshi)
    }


    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApis = retrofit.create(UserApis::class.java)

    @Provides
    @Singleton
    fun providesRemoteDataSource(apiService: UserApis): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesMainRepository(
        remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource
    ): UserRepository = UserRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideMoshiInstance(): Moshi = Moshi.Builder().build()


}