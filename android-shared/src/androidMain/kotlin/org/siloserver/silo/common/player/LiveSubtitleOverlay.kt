package org.siloserver.silo.common.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.siloserver.silo.common.R
import org.siloserver.silo.playback.LiveSubtitleTrack

private const val LIVE_SUBTITLE_TICK_MS = 120L

/** Fork: how the live overlay is sized on one device. */
data class LiveSubtitleOverlayStyle(
    val fontSize: TextUnit,
    val lineHeight: TextUnit,
    val sidePadding: Dp,
    val bottomPadding: Dp,
    val maxWidth: Dp,
) {
    companion object {
        val Tv = LiveSubtitleOverlayStyle(26.sp, 34.sp, 48.dp, 40.dp, 1100.dp)
        val Phone = LiveSubtitleOverlayStyle(18.sp, 24.sp, 16.dp, 24.dp, 720.dp)
    }
}

/**
 * Fork: draws a running AI job's streamed lines over the picture, on phone
 * and TV.
 *
 * The server sends cues playhead-first while a translation or transcription
 * runs, long before the finished subtitle file exists. Media3's own subtitle
 * view is hidden meanwhile (the caller does that), so the viewer sees one set
 * of lines; once the finished track is loaded and selected, the caller drops
 * this overlay and Media3 takes over again.
 *
 * @param positionSourceSeconds the playhead in source (file) seconds, the clock
 *   the server's cue times use.
 * @param bottomLiftPx extra lift so lines clear the transport controls.
 */
@Composable
fun LiveSubtitleOverlay(
    track: LiveSubtitleTrack,
    positionSourceSeconds: () -> Double,
    bottomLiftPx: Int,
    hidden: Boolean,
    style: LiveSubtitleOverlayStyle,
) {
    val latestTrack by rememberUpdatedState(track)
    val latestPosition by rememberUpdatedState(positionSourceSeconds)
    var lines by remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(Unit) {
        while (isActive) {
            val next = latestTrack.textAt(latestPosition())
            if (next != lines) lines = next
            delay(LIVE_SUBTITLE_TICK_MS)
        }
    }
    if (hidden) return
    // Nothing has arrived yet: say so instead of showing nothing.
    val waiting = stringResource(
        if (track.transcribing) R.string.fork_liveSubtitle_transcribing else R.string.fork_liveSubtitle_translating,
    )
    val shown = when {
        lines.isNotEmpty() -> lines.joinToString("\n")
        track.cues.isEmpty() -> waiting
        else -> return
    }
    val liftDp = with(LocalDensity.current) { bottomLiftPx.toDp() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = style.sidePadding, end = style.sidePadding, bottom = style.bottomPadding + liftDp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        BasicText(
            shown,
            style = TextStyle(
                color = Color.White,
                fontSize = style.fontSize,
                lineHeight = style.lineHeight,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                shadow = Shadow(color = Color.Black, offset = Offset(2f, 2f), blurRadius = 6f),
            ),
            modifier = Modifier
                .widthIn(max = style.maxWidth)
                .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(6.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
        )
    }
}
