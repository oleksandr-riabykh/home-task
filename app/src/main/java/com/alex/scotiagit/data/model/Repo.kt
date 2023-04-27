package com.alex.scotiagit.data.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name")
    var name: String?,

    @SerializedName("description")
    var description: String?,

    @SerializedName("updated_at")
    var updatedAt: String?,

    @SerializedName("stargazers_count")
    var stargazersCount: Int?,

    @SerializedName("forks")
    var forks: Int?
)