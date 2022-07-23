package com.example.deviantartviewer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deviantartviewer.data.local.db.dao.UserDao
import com.example.deviantartviewer.data.local.db.entity.UserEntity
import javax.inject.Singleton


@Singleton
@Database(
        entities = [
            UserEntity::class,
        ],
        exportSchema = false,
        version = 1
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun userDao(): UserDao
}