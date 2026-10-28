package org.siloserver.silo.common.settings

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import org.siloserver.silo.common.player.MediaCodecCapabilitiesProbe

/**
 * Mirrors the active profile's HEVC level override into
 * [MediaCodecCapabilitiesProbe], the single source every capability report
 * (playback, downloads, diagnostics) reads, so no call site needs the setting
 * threaded through it.
 */
object HevcLevelOverrideBinder {
    fun start(scope: CoroutineScope, store: PlayerSettingsStore) {
        scope.launch {
            store.hevcLevelOverrideFlow
                // A settings read failure (DataStore I/O, corruption) would
                // otherwise end the collector and freeze the probe at its last
                // value until the app restarts. Back off and resubscribe.
                .retryWhen { cause, attempt ->
                    Log.w(TAG, "HEVC level override read failed: ${cause::class.simpleName}")
                    delay(minOf(30_000L, 1_000L shl attempt.coerceAtMost(5).toInt()))
                    true
                }
                .distinctUntilChanged()
                .collect { level -> MediaCodecCapabilitiesProbe.hevcLevelOverride = level }
        }
    }

    private const val TAG = "HevcLevelOverride"
}
