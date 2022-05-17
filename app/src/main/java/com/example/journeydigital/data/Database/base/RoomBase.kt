package com.example.journeydigital.data.Database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.journeydigital.data.Database.dao.CommentDao
import com.example.journeydigital.data.Database.dao.DashboardPostDao
import com.example.journeydigital.data.model.DashboardResponse

    // Annotates class to be a Room Database with a table (entity) of the Word class
    @Database(entities = arrayOf(DashboardResponse::class), version = 1, exportSchema = false)
    public abstract class RoomBase : RoomDatabase() {

        abstract fun postDao(): DashboardPostDao
        abstract fun commentDao(): CommentDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: RoomBase? = null

            fun getDatabase(context: Context): RoomBase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomBase::class.java,
                        "JourneyDigital"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
    }
