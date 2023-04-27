package com.alex.scotiagit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.scotiagit.data.model.Repo
import com.alex.scotiagit.data.model.User
import com.alex.scotiagit.data.repositories.GitRepository
import com.alex.scotiagit.ui.home.model.HomeUiModel
import com.alex.scotiagit.ui.home.model.HomeUiState
import com.alex.scotiagit.ui.home.model.RepoUiModel
import com.alex.scotiagit.ui.home.model.UserUiModel
import com.alex.scotiagit.utils.toDisplayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gitRepository: GitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.InitState)
    val uiState: StateFlow<HomeUiState> = _uiState

    /**
     * We could make proper error handling using CoroutineExceptionHandler, customize error when it needed
     */
    fun loadGitInfo(userId: String?) {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {

                // instead of that we might just disable search button and
                // enable once anything is present in the search input
                if (userId == null || userId.isEmpty()) {
                    throw Exception("Input is empty.")
                } else {
                    val user = gitRepository.getUserInfo(userId)
                    val repository = gitRepository.getRepositories(userId)
                    if (repository == null || repository.isEmpty()) {
                        _uiState.value = HomeUiState.EmptyReposLoaded(user.toUIUser())
                    } else {
                        val result = parseNetworkData(user, repository)
                        _uiState.value = HomeUiState.FullDataLoaded(result)
                    }
                }

            } catch (error: Exception) {
                _uiState.value = HomeUiState.Error(error)
            }
        }
    }

    // as an option probably better to use extensions for that approach
    private fun parseNetworkData(user: User, repo: List<Repo>): HomeUiModel {
        // just an example, we could have customize application Exception. I think is better to handle such errors here, in the viewmodel level
        // will go in the string resources
        // need to handle empty user name based on requirements, currently no requirements. Maybe use user id
        val uiUser = UserUiModel(user.name ?: "No Name", user.photo)
        val uiRepos = repo.map { networkRepo ->
            RepoUiModel(
                networkRepo.name ?: throw Exception("Empty repository name"),
                networkRepo.description
                    ?: "No Description", // can be any other logic in case no description
                networkRepo.updatedAt?.toDisplayDate() ?: throw Exception("Empty updated date"),
                networkRepo.stargazersCount ?: 0,
                networkRepo.forks ?: 0
            )
        }

        return HomeUiModel(uiUser, uiRepos)
    }

    private fun User.toUIUser(): UserUiModel =
        UserUiModel(name ?: "No Name", photo)
}
