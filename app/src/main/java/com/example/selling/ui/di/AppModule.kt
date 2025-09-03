package com.example.selling.ui.di

import com.example.selling.data.repository.abstract_interface.AuthRepository
import com.example.selling.data.repository.AuthRepositoryImpl
import com.example.selling.data.repository.ProductRepositoryImpl
import com.example.selling.data.repository.abstract_interface.ProductRepository
import com.example.selling.ui.viewModel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val myAppModules = module {
    factory { Firebase.auth } // Firebase Auth
    factory{ Firebase.database.reference} // Firebase Firestore}
    factory { Firebase.firestore }
    viewModel { AuthViewModel(get()) }
    single<AuthRepository> {AuthRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(),get()) }


}