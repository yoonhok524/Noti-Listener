package com.aaron.notilistener.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aaron.notilistener.data.db.entity.NotiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entity: NotiEntity)

    @Query("SELECT * FROM NotiEntity ORDER BY time DESC")
    fun loadAll(): Flow<List<NotiEntity>>
}