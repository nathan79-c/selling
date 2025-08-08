package com.example.selling.ui

class UserViewModel {
}
sealed class UserUiState{
    data object IsLogin: UserUiState()
    data object IsNotLogin: UserUiState()

}