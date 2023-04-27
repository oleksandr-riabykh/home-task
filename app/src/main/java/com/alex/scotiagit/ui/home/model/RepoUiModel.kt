package com.alex.scotiagit.ui.home.model

data class RepoUiModel(
    var name: String,
    var description: String,
    var updatedAt: String,
    var stargazersCount: Int,
    var forks: Int
)