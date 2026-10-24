# Silo 1.0.0-hevc APKs

Optimized release builds (R8 + baseline profile) of the
[edrock200/silo-android](https://github.com/edrock200/silo-android) fork:
upstream Silo Android 1.0.0, newer upstream fixes, and an **HEVC Level Override**
setting. Built from branch `feat/hevc-level-override-v2` at commit edc23052.
See [CHANGELOG.md](CHANGELOG.md).

| File | Device | Version | Version code |
|---|---|---|---|
| [`silo-tv-1.0.0-hevc-release.apk`](silo-tv-1.0.0-hevc-release.apk) | Android TV (NVIDIA Shield, etc.) | 1.0.0-hevc (26) | 220000053 |
| [`silo-phone-1.0.0-hevc-release.apk`](silo-phone-1.0.0-hevc-release.apk) | Phone / tablet | 1.0.0-hevc (26) | 220000052 |

## Install

- Universal APKs (all CPU types), signed with the Android debug key. They install
  as `org.siloserver.silo` and update earlier builds from this branch in place.
- They can't install over the official Play Store or GitHub release builds,
  which use a different key. Uninstall those first; you'll need to sign in again.

## Use the HEVC Level Override

- TV: Settings → Streaming → **HEVC Level Override**
- Phone: Settings → Playback → Decoder → **HEVC Level Override**

Choose **Level 6.2** to direct play files tagged HEVC Main 10 level 6.2. Auto
reports only what the decoder advertises. Files that truly exceed the decoder may
stutter or fail.
