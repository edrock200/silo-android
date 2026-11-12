package org.siloserver.silo.common.player

import org.siloserver.silo.common.ui.LanguageNames
import org.siloserver.silo.model.subtitles.SubtitleAiJobKind

/** The AI subtitle job an audio-mode request sends. */
data class AiTranscribePlan(
    val kind: String,
    /** Null when the track has no usable language tag. */
    val sourceLanguage: String?,
    /** For a plain transcription, a hint for the spoken language ("" for none). */
    val targetLanguage: String,
)

/**
 * Fork: which job a Transcribe press sends, shared by phone and TV.
 *
 * - Audio tagged with the target's language: plain transcription.
 * - Audio tagged with another language: transcribe, then translate.
 * - Audio with no usable tag (blank, "und", unknown): plain transcription with
 *   the target as the language hint. Before, an untagged track always ran
 *   transcribe-and-translate, which paid for an English → English pass,
 *   delayed the first live lines and read "Transcribing & translating".
 *
 * [canTranslate] false (translation off on the server) always transcribes,
 * without a hint, as before.
 */
fun aiTranscribePlan(audioLanguage: String?, targetLanguage: String, canTranslate: Boolean): AiTranscribePlan {
    val tagged = LanguageNames.isKnown(audioLanguage)
    val source = audioLanguage?.trim()?.takeIf { tagged }
    val target = targetLanguage.trim()
    return when {
        !canTranslate -> AiTranscribePlan(SubtitleAiJobKind.Transcribe, source, "")
        !tagged -> AiTranscribePlan(SubtitleAiJobKind.Transcribe, null, target)
        isSameAiLanguage(source, target) -> AiTranscribePlan(SubtitleAiJobKind.Transcribe, source, "")
        else -> AiTranscribePlan(SubtitleAiJobKind.TranscribeTranslate, source, target)
    }
}

/** Compares normalized codes, so "eng" audio matches an "en" target. */
fun isSameAiLanguage(source: String?, target: String): Boolean {
    val code = source?.trim()?.lowercase().orEmpty()
    if (code.isEmpty() || !LanguageNames.isKnown(code)) return false
    if (code == target.trim().lowercase()) return true
    return LanguageNames.searchCode(code) == LanguageNames.searchCode(target)
}
