package com.example.journeydigital.data.Database.reposirories

import androidx.annotation.WorkerThread
import com.example.journeydigital.data.Database.dao.DashboardPostDao
import com.example.journeydigital.data.model.DashboardResponse
import kotlinx.coroutines.flow.Flow

class PostRepositories (private val postDao: DashboardPostDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPost: List<DashboardResponse> = postDao.getAllPost()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: DashboardResponse) {
        postDao.insertPost(word)
    }
}