# Silo debug APKs with HEVC Level Override

Built from branch `feat/hevc-level-override` at commit a80ea27.

- `silo-tv-hevc-override-debug.apk`: Android TV (NVIDIA Shield, etc.)
- `silo-phone-hevc-override-debug.apk`: phone/tablet

Universal (all-ABI) debug builds, signed with the Android debug key. They install
as `org.siloserver.silo`; uninstall any Play Store build first.

Turn it on under Settings > Streaming > HEVC Level Override (TV) or
Settings > Playback > Decoder (phone). Choose Level 6.2 to direct play
files tagged HEVC Main 10 level 6.2.
