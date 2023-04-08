package com.aaron.notilistener.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pkgName: String,
    val time: Long = System.currentTimeMillis(),
    val title: String? = null,
    val text: String? = null,
    val bigText: String? = null,
    val meta: String
)