package org.siloserver.silo.common.settings

import kotlin.test.Test
import kotlin.test.assertEquals

class HevcLevelOverrideOptionsTest {
    @Test
    fun offersOnlyLevelsAboveTheDecoder() {
        assertEquals(listOf(0, 156, 180, 183, 186), HevcLevelOverrideOptions.options(decoderMaxLevel = 153, current = 0))
        assertEquals(listOf(0, 150, 153, 156, 180, 183, 186), HevcLevelOverrideOptions.options(decoderMaxLevel = null, current = 0))
        assertEquals(listOf(0), HevcLevelOverrideOptions.options(decoderMaxLevel = 186, current = 0))
    }

    @Test
    fun keepsTheCurrentChoiceListed() {
        assertEquals(listOf(0, 153, 156, 180, 183, 186), HevcLevelOverrideOptions.options(decoderMaxLevel = 153, current = 153))
    }

    @Test
    fun namesLevels() {
        assertEquals("5.0", HevcLevelOverrideOptions.levelName(150))
        assertEquals("5.1", HevcLevelOverrideOptions.levelName(153))
        assertEquals("6.2", HevcLevelOverrideOptions.levelName(186))
    }
}
