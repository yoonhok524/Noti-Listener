package com.aaron.notilistener.di

import android.content.Context
import androidx.room.Room
import com.aaron.notilistener.data.db.NotiDatabase
import com.aaron.notilistener.data.db.dao.NotiDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NotiDatabase {
        return Room.databaseBuilder(
            appContext,
            NotiDatabase::class.java, "noti-db"
        ).build()
    }

    @Provides
    fun provideEtfDao(database: NotiDatabase): NotiDao {
        return database.notiDao()
    }

    @Provides
    internal fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}