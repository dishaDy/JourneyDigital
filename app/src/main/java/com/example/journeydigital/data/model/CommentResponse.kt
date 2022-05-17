package com.example.journeydigital.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Comment")
data class CommentResponse(
    @SerializedName("postId") val postId: Int,
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
)
