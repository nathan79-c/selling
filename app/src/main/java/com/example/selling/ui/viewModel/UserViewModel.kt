package com.example.selling.ui.viewModel

import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
}
sealed class UserUiState{
    data object IsLogin: UserUiState()
    data object IsNotLogin: UserUiState()

}