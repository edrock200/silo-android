package org.siloserver.silo.common.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.siloserver.silo.common.R

/**
 * Fork: the choices for the HEVC Level Override, shared by phone and TV. Levels
 * are normalized as level x 30 (0 = Auto, report the decoder's own levels). See
 * [org.siloserver.silo.model.settings.PlaybackSettingsKeys.HevcLevelOverride].
 */
object HevcLevelOverrideOptions {
    /** Levels 5.0 to 6.2; below 5.0 no file is worth forcing. */
    val levels: List<Int> = listOf(150, 153, 156, 180, 183, 186)

    /**
     * Auto, then only the levels above [decoderMaxLevel] (a level at or below
     * what the decoder reports changes nothing). The [current] choice stays
     * listed so the picker can show it.
     */
    fun options(decoderMaxLevel: Int?, current: Int): List<Int> {
        val above = levels.filter { decoderMaxLevel == null || it > decoderMaxLevel }
        val kept = current.takeIf { it > 0 && it !in above }
        return listOf(0) + (above + listOfNotNull(kept)).sorted()
    }

    /** "5.1" for 153. */
    fun levelName(level: Int): String = "${level / 30}.${(level % 30) / 3}"
}

/** "Auto (decoder reports 5.1)", "Auto" or "Level 6.1". */
@Composable
fun hevcLevelOverrideLabel(level: Int, decoderMaxLevel: Int?): String = when {
    level > 0 -> stringResource(R.string.fork_hevcLevelOverride_level, HevcLevelOverrideOptions.levelName(level))
    decoderMaxLevel != null -> stringResource(
        R.string.fork_hevcLevelOverride_autoWithDecoder,
        HevcLevelOverrideOptions.levelName(decoderMaxLevel),
    )
    else -> stringResource(R.string.fork_hevcLevelOverride_auto)
}
