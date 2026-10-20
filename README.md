# Silo APKs with HEVC Level Override

Built from branch `feat/hevc-level-override` at commit a80ea27.

**Use the release builds.** They are optimized (R8 + baseline profile) and
much smoother than the debug builds.

| File | Device | Build |
|---|---|---|
| `silo-tv-hevc-override-release.apk` | Android TV (NVIDIA Shield, etc.) | optimized release |
| `silo-phone-hevc-override-release.apk` | phone/tablet | optimized release |
| `silo-tv-hevc-override-debug.apk` | Android TV | debug (slow, for troubleshooting) |
| `silo-phone-hevc-override-debug.apk` | phone/tablet | debug (slow, for troubleshooting) |

All are universal (all-ABI) and signed with the Android debug key. They install
as `org.siloserver.silo`; uninstall any Play Store build first.

Turn it on under Settings > Streaming > HEVC Level Override (TV) or
Settings > Playback > Decoder (phone). Choose Level 6.2 to direct play
files tagged HEVC Main 10 level 6.2.
