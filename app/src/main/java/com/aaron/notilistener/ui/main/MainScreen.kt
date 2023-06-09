package com.aaron.notilistener.ui.main

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.aaron.notilistener.data.db.entity.NotiEntity
import java.text.SimpleDateFormat
import java.util.Date

private val sdf = SimpleDateFormat("MM-dd\nHH:mm")

@Composable
fun MainScreen(
    vm: MainViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        vm.checkPerm(context)
    })

    val notifications: List<NotiEntity> by vm.notiList.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    startActivity(
                        context,
                        Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"),
                        null
                    )
                }
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Permission")
            Switch(
                checked = vm.hasNotiReadPerm,
                onCheckedChange = {},
                enabled = false
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colors.onBackground)
        )
        
        LazyColumn {
            itemsIndexed(notifications) { _, noti ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(
                            text = noti.pkgName,
                            style = MaterialTheme.typography.caption
                        )
                        Text(
                            text = noti.title ?: "",
                            style = MaterialTheme.typography.subtitle2
                        )
                        if (!noti.text.isNullOrBlank()) {
                            Text(
                                text = "[S-text] " + noti.text,
                                style = MaterialTheme.typography.body2
                            )
                        }
                        if (!noti.bigText.isNullOrBlank()) {
                            Text(
                                text = "[L-text] " + noti.bigText,
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Text(
                            text = "[meta] " + noti.meta,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = sdf.format(Date(noti.time)),
                        style = MaterialTheme.typography.caption
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(MaterialTheme.colors.onBackground.copy(0.5F))
                )
            }
        }
    }
}