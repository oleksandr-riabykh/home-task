package com.alex.scotiagit.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    var name: String?,

    @SerializedName("avatar_url")
    var photo: String?
)