package com.aaron.notilistener.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.aaron.notilistener.common.toast
import com.aaron.notilistener.data.repo.NotiRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotiListenerService : NotificationListenerService() {

    @Inject
    lateinit var notiRepo: NotiRepository

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onNotificationRemoved(
        sbn: StatusBarNotification?,
        rankingMap: RankingMap?,
        reason: Int
    ) {
        super.onNotificationRemoved(sbn, rankingMap, reason)
        toast("알림 삭제!!")
    }

    override fun onNotificationPosted(noti: StatusBarNotification?) {
        super.onNotificationPosted(noti)
        if (noti == null) {
            return
        }

        scope.launch {
            val bundle = noti.notification.extras
            Log.d("TEST", "[test] packageName: ${noti.packageName}")
            Log.d("TEST", "[test] title: ${bundle.getString("android.title")}")
            Log.d("TEST", "[test] text: ${bundle.getString("android.text")}")
            Log.d("TEST", "[test] bigText: ${bundle.getString("android.bigText")}")
            notiRepo.save(
                noti.packageName,
                noti.postTime,
                title = bundle.getString("android.title"),
                body = bundle.getString("android.text")?.trim()
                    ?.ifBlank { bundle.getString("android.bigText")?.trim() ?: "" },
            )
        }
    }

    companion object {
        private const val TAG = "NotiListenerService"
    }
}