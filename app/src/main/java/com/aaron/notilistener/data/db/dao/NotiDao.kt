package com.aaron.notilistener.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aaron.notilistener.data.db.entity.NotiEntity

@Dao
interface NotiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: NotiEntity)

    @Query("SELECT * FROM NotiEntity")
    suspend fun loadAll(): List<NotiEntity>
}