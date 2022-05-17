package com.example.journeydigital.data.model

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("postId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
)
