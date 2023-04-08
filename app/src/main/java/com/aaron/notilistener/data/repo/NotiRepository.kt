package com.aaron.notilistener.data.repo

import com.aaron.notilistener.data.db.dao.NotiDao
import com.aaron.notilistener.data.db.entity.NotiEntity
import kotlinx.coroutines.CoroutineDispatcher
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
        body: String?
    ) {
        withContext(ioDispatcher) {
            notiDao.insert(NotiEntity(pkgName = pkgName, time = time, title = title, body = body))
        }
    }

    suspend fun getAll(): List<NotiEntity> {
        return withContext(ioDispatcher) {
            notiDao.loadAll()
        }
    }
}