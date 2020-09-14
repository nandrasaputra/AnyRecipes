package com.endiar.anyrecipes

import android.app.Application
import com.endiar.anyrecipes.core.di.databaseModule
import com.endiar.anyrecipes.core.di.networkModule
import com.endiar.anyrecipes.core.di.repositoryModule
import com.endiar.anyrecipes.di.useCaseModule
import com.endiar.anyrecipes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class AnyRecipeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AnyRecipeApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}