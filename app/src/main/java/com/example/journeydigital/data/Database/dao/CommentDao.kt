package com.example.journeydigital.data.Database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journeydigital.data.model.CommentResponse

@Dao
interface CommentDao {
    /**
     * To get comment with specific id
     */
    @Query("SELECT * FROM Comment WHERE postId=:postId")
    fun getAllCommentById(postId: Int): List<CommentResponse>

    /**
     * Insert comment
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComment(post: MutableList<CommentResponse>)

    /**
     * Delete comment with specific id
     */
    @Query("DELETE FROM Comment WHERE postId=:postId")
    fun deleteAllCommentById(postId: Int)
}