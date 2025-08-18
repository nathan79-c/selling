package com.example.selling.ui.di

import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.selling.data.repository.abstract_interface.AuthRepository
import com.example.selling.data.repository.AuthRepositoryImpl
import com.example.selling.ui.viewModel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.coroutines.EmptyCoroutineContext.get

val myAppModules = module {
    factory { Firebase.auth } // Firebase Auth
    viewModel { AuthViewModel(get()) }
    single<AuthRepository> {AuthRepositoryImpl(get()) }


}