package com.example.selling.ui.di

import com.example.selling.data.repository.AuthRepository
import com.example.selling.ui.viewModel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val myAppModules = module {
    viewModelOf(::AuthViewModel)
    single { AuthRepository(get()) }
    factory { Firebase.auth(get()) } // Firebase Auth

}