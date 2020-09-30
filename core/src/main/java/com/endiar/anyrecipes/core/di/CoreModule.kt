package com.endiar.anyrecipes.core.di

import androidx.room.Room
import com.endiar.anyrecipes.core.R
import com.endiar.anyrecipes.core.data.AnyRecipeRepository
import com.endiar.anyrecipes.core.data.source.local.LocalDataSource
import com.endiar.anyrecipes.core.data.source.local.room.AnyRecipeDatabase
import com.endiar.anyrecipes.core.data.source.remote.RemoteDataSource
import com.endiar.anyrecipes.core.data.source.remote.network.SpoonacularApiService
import com.endiar.anyrecipes.core.domain.repository.IAnyRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("anyrecipe".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AnyRecipeDatabase::class.java, "AnyRecipe.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostName = "api.spoonacular.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostName, "sha256/DjBNmOWrmE91DLaH6gk+96MMBNjNs4/cbGxgvWLZi18=")
            .add(hostName, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(hostName, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()
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
            .certificatePinner(certificatePinner)
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
    single<IAnyRepository> { AnyRecipeRepository(get(), get()) }
}