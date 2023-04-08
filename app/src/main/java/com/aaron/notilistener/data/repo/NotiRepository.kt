package com.aaron.notilistener.data.repo

import com.aaron.notilistener.data.db.dao.NotiDao
import com.aaron.notilistener.data.db.entity.NotiEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotiRepository @Inject constructor(
    private val notiDao: NotiDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun save(
        pkgName: String,
        time: Long,
        title: String?,
        text: String?,
        bigText: String?,
        meta: String
    ) {
        withContext(ioDispatcher) {
            notiDao.insert(NotiEntity(pkgName = pkgName, time = time, title = title, text = text, bigText = bigText, meta = meta))
        }
    }

    suspend fun getAll(): Flow<List<NotiEntity>> {
        return withContext(ioDispatcher) {
            notiDao.loadAll()
        }
    }
}