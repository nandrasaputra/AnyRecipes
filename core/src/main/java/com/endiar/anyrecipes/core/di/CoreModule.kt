package com.endiar.anyrecipes.core.di

import androidx.room.Room
import com.endiar.anyrecipes.core.BuildConfig
import com.endiar.anyrecipes.core.R
import com.endiar.anyrecipes.core.data.AnyRecipeRepository
import com.endiar.anyrecipes.core.data.source.local.LocalDataSource
import com.endiar.anyrecipes.core.data.source.local.room.AnyRecipeDatabase
import com.endiar.anyrecipes.core.data.source.remote.RemoteDataSource
import com.endiar.anyrecipes.core.data.source.remote.network.SpoonacularApiService
import com.endiar.anyrecipes.core.domain.repository.IAnyRepository
import com.endiar.anyrecipes.core.utils.AppExecutors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AnyRecipeDatabase>().favoriteRecipeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AnyRecipeDatabase::class.java, "AnyRecipe.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val requestInterceptor = Interceptor { chain ->
            val httpUrl = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("apiKey", androidContext().getString(R.string.SPOONACULAR_API_KEY))
                .build()
            val request = chain.request()
                .newBuilder()
                .url(httpUrl)
                .build()
            return@Interceptor chain.proceed(request)
        }

        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/recipes/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(SpoonacularApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IAnyRepository> { AnyRecipeRepository(get(), get(), get()) }
}