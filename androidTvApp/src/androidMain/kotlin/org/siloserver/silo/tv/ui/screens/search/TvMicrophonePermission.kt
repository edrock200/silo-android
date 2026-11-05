package org.siloserver.silo.tv.ui.screens.search

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

// Asked at most once per app process, so leaving and re-entering Search after
// a denial doesn't put the dialog in front of the viewer every time.
@Volatile
private var askedThisProcess = false

/**
 * Fork: asks for the microphone the first time Search opens.
 *
 * Voice typing in the search field comes from the on-screen keyboard's mic key.
 * Some Android TV keyboards only listen when the app holding the text field
 * has microphone access, so without it the mic key does nothing. Android stops
 * showing the dialog by itself after the viewer denies it twice.
 */
@Composable
internal fun RequestMicrophonePermissionOnce() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    LaunchedEffect(Unit) {
        if (askedThisProcess) return@LaunchedEffect
        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_GRANTED
        if (granted) return@LaunchedEffect
        askedThisProcess = true
        runCatching { launcher.launch(Manifest.permission.RECORD_AUDIO) }
    }
}
