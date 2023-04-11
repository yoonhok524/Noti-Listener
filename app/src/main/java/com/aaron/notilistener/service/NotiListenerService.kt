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
            Log.d(TAG, "[onNotificationPosted] packageName: ${noti.packageName}")
            Log.d(TAG, "[onNotificationPosted] title: ${bundle.getString("android.title")}")
            Log.d(TAG, "[onNotificationPosted] text: ${bundle.getString("android.text")}")
            Log.d(TAG, "[onNotificationPosted] bigText: ${bundle.getString("android.bigText")}")

            val meta: String = "groupKey: " + noti.groupKey + ", key: " + noti.key + ", category: " + noti.notification.category + ", channelId: " + noti.notification.channelId + ", group: " + noti.notification.group + ", settingsText: " + noti.notification.settingsText + ", tickerText: " + noti.notification.tickerText
            Log.d(TAG, "[onNotificationPosted] meta: $meta")

            if (noti.packageName == "com.kakao.talk") {
                notiRepo.save(
                    noti.packageName,
                    noti.postTime,
                    title = bundle.getString("android.title"),
                    text = bundle.getString("android.text")?.trim(),
                    bigText = bundle.getString("android.bigText")?.trim(),
                    meta = meta
                )
            }
        }
    }

    companion object {
        private const val TAG = "NotiListenerService"
    }
}