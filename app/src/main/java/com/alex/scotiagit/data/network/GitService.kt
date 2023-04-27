package com.alex.scotiagit.data.network

import com.alex.scotiagit.data.model.Repo
import com.alex.scotiagit.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

const val SERVICE_HOST = "https://api.github.com/"
const val USER_ID = "userid"
const val ENDPOINT_USERS = "users/{$USER_ID}"
const val ENDPOINT_USERS_REPO = "users/{$USER_ID}/repos"


interface GitService {
    @GET(ENDPOINT_USERS_REPO)
    suspend fun getRepositories(@Path(USER_ID) userId: String): List<Repo>?

    @GET(ENDPOINT_USERS)
    suspend fun getUserInfo(@Path(USER_ID) userId: String): User
}