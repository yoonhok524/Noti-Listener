package com.aaron.notilistener.ui.main

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

@Composable
fun MainScreen(
    vm: MainViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        vm.checkPerm(context)
    })

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    startActivity(context, Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), null)
                }
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Permission")
            Switch(
                checked = vm.hasNotiReadPerm,
                onCheckedChange = {},
                enabled = false
            )
        }
    }
}