package com.alex.scotiagit.ui.home.model

data class HomeUiModel(
    /**
     * can not be null, otherwise wrong user id.
     * It might return 404 if wrong user id.
     */
    var user: UserUiModel,
    /**
     * can be empty.
     */
    var repos: List<RepoUiModel>
)