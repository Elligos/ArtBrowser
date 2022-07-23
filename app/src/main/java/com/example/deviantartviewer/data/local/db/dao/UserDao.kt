package com.example.deviantartviewer.data.local.db.dao

import androidx.room.*
import com.example.deviantartviewer.data.local.db.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class UserDao {

    @Transaction
    @Query("SELECT * FROM user_entity")
    abstract fun getAll(): Single<List<UserEntity>>

    @Query("SELECT * FROM user_entity WHERE  userid = :id")
    abstract fun getUser(id : String) : Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserInfo(entity: UserEntity) : Completable

    @Update
    abstract fun updateUserInfo(entity: UserEntity) : Completable

    @Delete
    abstract fun delete(entity: UserEntity) : Completable

    @Query("DELETE FROM user_entity")
    abstract fun deleteAll() : Completable
}