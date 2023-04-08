package com.aaron.notilistener.ui.main

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.notilistener.data.db.entity.NotiEntity
import com.aaron.notilistener.data.repo.NotiRepository
import com.aaron.notilistener.service.NotiListenerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notiRepo: NotiRepository
): ViewModel() {

    var hasNotiReadPerm: Boolean by mutableStateOf(false)
        private set

    private val _notiList = MutableStateFlow<List<NotiEntity>>(emptyList())
    val notiList: StateFlow<List<NotiEntity>> = _notiList

    init {
        viewModelScope.launch {
            notiRepo.getAll().collectLatest { notifications ->
                _notiList.value = notifications
            }
        }
    }

    fun checkPerm(context: Context) {
        hasNotiReadPerm = ContextCompat.getSystemService(context, NotificationManager::class.java)
            ?.isNotificationListenerAccessGranted(
                ComponentName(context, NotiListenerService::class.java)
            ) == true
    }

}