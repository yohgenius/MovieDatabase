package com.example.moviedatabase.di

import android.content.Context
import androidx.room.Room
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.data.Repository
import com.example.moviedatabase.data.local.room.MovieDB
import com.example.moviedatabase.data.local.room.MovieDao
import com.example.moviedatabase.data.remote.network.ApiService
import com.example.moviedatabase.domain.RepositoryInterface
import com.example.moviedatabase.domain.usecase.Interactor
import com.example.moviedatabase.domain.usecase.UseCase
import com.example.moviedatabase.external.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUseCase(interactor: Interactor): UseCase = interactor

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    @Singleton
    fun provideDao(githubDb: MovieDB): MovieDao {
        return githubDb.dao()
    }

    // Database Module
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDB {
        return Room.databaseBuilder(
            context,
            MovieDB::class.java,
            "Movies.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRepository(repository: Repository): RepositoryInterface {
        return repository
    }
}