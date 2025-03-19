package com.lapoushko.ui

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

/**
 * @author Lapoushko
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionCheck(permission: String){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionState = rememberPermissionState(permission = permission)
        LaunchedEffect(Unit) {
            if (!permissionState.status.isGranted){
                permissionState.launchPermissionRequest()
            }
        }
    }
}