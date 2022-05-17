package com.example.journeydigital.data.Database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journeydigital.data.model.DashboardResponse

@Dao
interface DashboardPostDao  {
    @Query("SELECT * FROM Post ORDER BY id ASC")
    fun getAllPost(): List<DashboardResponse>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: DashboardResponse)

    @Query("DELETE FROM post")
    suspend fun deleteAll()
}