package com.example.journeydigital.data.Database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journeydigital.data.model.DashboardResponse

@Dao
interface DashboardPostDao {
    /**
     * Get All post assencding to ID
     */
    @Query("SELECT * FROM Post ORDER BY id ASC")
     fun getAllPost(): List<DashboardResponse>

    /**
     * Insert post
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertPost(post: MutableList<DashboardResponse>)

    /**
     * Delete All post
     */
    @Query("DELETE FROM Post")
      fun deleteAll()
}