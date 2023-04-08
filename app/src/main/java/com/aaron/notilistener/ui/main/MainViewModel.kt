package com.aaron.notilistener.ui.main

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.aaron.notilistener.service.NotiListenerService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    var hasNotiReadPerm: Boolean by mutableStateOf(false)
        private set

    fun checkPerm(context: Context) {
        hasNotiReadPerm = ContextCompat.getSystemService(context, NotificationManager::class.java)
            ?.isNotificationListenerAccessGranted(
                ComponentName(context, NotiListenerService::class.java)
            ) == true
    }

}