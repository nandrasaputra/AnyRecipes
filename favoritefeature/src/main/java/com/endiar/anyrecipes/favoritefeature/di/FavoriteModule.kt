package com.endiar.anyrecipes.favoritefeature.di

import com.endiar.anyrecipes.favoritefeature.ui.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}