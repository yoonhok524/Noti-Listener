package com.aaron.notilistener.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aaron.notilistener.data.db.dao.NotiDao
import com.aaron.notilistener.data.db.entity.NotiEntity

@Database(
    entities = [
        NotiEntity::class,
    ],
    version = 1,
)
abstract class NotiDatabase: RoomDatabase() {

    abstract fun notiDao(): NotiDao

}