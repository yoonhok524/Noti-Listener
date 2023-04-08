package com.aaron.notilistener.service

import android.content.ComponentName
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import com.aaron.notilistener.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotiListenerService : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "[FG][noti] onListenerConnected()")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d(TAG, "[FG][noti] onListenerDisconnected()")
        requestRebind(ComponentName(this, NotiListenerService::class.java))
    }

    override fun onNotificationRemoved(
        sbn: StatusBarNotification?,
        rankingMap: RankingMap?,
        reason: Int
    ) {
        super.onNotificationRemoved(sbn, rankingMap, reason)
        toast("알림 삭제!")
    }

    override fun onNotificationPosted(noti: StatusBarNotification?) {
        super.onNotificationPosted(noti)
        Log.d(TAG, "[FG][noti] onNotificationPosted - ${noti?.packageName}")
        if (noti?.packageName != KAKAOTALK_PKG) {
            toast(noti?.packageName, Toast.LENGTH_LONG)
            return
        }

        val bundle = noti.notification.extras
        val title = bundle.getString(SENDER)
        val body = bundle.containsKey(BIG_TEXT)

        scope.launch {
//            save(title, body, noti.postTime)
        }
    }

    companion object {
        private const val TAG = "NotiListenerService"
        private const val KAKAOTALK_PKG = "com.kakao.talk"
        private const val BIG_TEXT = "android.bigText"
        private const val SENDER = "android.title"
    }
}