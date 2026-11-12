package org.siloserver.silo.tv.ui.screens.search

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

private const val PREFS_NAME = "silo_tv_microphone_prompt"
private const val KEY_ASKED = "asked"

/**
 * Fork: asks for the microphone the first time Search opens.
 *
 * Voice typing in the search field comes from the on-screen keyboard's mic key.
 * Some Android TV keyboards only listen when the app holding the text field
 * has microphone access, so without it the mic key does nothing.
 *
 * The prompt is shown once per install: a viewer who denies it isn't asked
 * again on later launches, and can still grant it in system settings or
 * through the Search mic button where that asks for it.
 */
@Composable
internal fun RequestMicrophonePermissionOnce() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_GRANTED
        if (granted) return@LaunchedEffect
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(KEY_ASKED, false)) return@LaunchedEffect
        prefs.edit().putBoolean(KEY_ASKED, true).apply()
        runCatching { launcher.launch(Manifest.permission.RECORD_AUDIO) }
    }
}
