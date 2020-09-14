package com.endiar.anyrecipes.di

import com.endiar.anyrecipes.core.domain.usecase.LocalInteractor
import com.endiar.anyrecipes.core.domain.usecase.LocalUseCase
import com.endiar.anyrecipes.core.domain.usecase.RemoteInteractor
import com.endiar.anyrecipes.core.domain.usecase.RemoteUseCase
import com.endiar.anyrecipes.ui.detail.DetailViewModel
import com.endiar.anyrecipes.ui.fridge.FridgeViewModel
import com.endiar.anyrecipes.ui.home.HomeViewModel
import com.endiar.anyrecipes.ui.resultbyingredients.ResultByIngredientsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<LocalUseCase> { LocalInteractor(get()) }
    factory<RemoteUseCase> { RemoteInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { ResultByIngredientsViewModel(get()) }
    viewModel { FridgeViewModel() }
}