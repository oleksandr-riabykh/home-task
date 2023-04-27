package com.alex.scotiagit.ui.home.model

sealed class HomeUiState {
    object InitState : HomeUiState()
    object Loading : HomeUiState()
    data class EmptyReposLoaded(val user: UserUiModel) : HomeUiState()
    data class FullDataLoaded(val data: HomeUiModel) : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
}