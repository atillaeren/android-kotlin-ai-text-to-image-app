package com.example.aiartcreator

import android.app.Application
import androidx.room.Room
import com.example.aiartcreator.database.ImageDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import com.example.aiartcreator.data.remote.ApiService
import com.example.aiartcreator.repository.Repository
import com.example.aiartcreator.viewmodel.AppViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(listOf(databaseModule, viewModelModule, repoModule, netWorkModule))
        }
    }

    private val databaseModule = module {

        single {
            Room.databaseBuilder(
                androidApplication(),
                ImageDatabase::class.java,
                "image_database"
            ).build()
        }
        single {
            val database = get<ImageDatabase>()
            database.imageDao()
        }


    }

    private val netWorkModule = module {
        single { provideOkHttpClient() }
        single { provideRetrofit(get(), BuildConfig.BASE_URL) }

        single { provideApiService(get()) }
    }

    private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    private val viewModelModule = module {
        // AppViewModel
        viewModel {
            AppViewModel(get(), get())
        }
    }

    private val repoModule = module {
        // Repository
        single {
            Repository(get())
        }
    }

    private fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}