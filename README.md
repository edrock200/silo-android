# Silo 1.0.0-hevc APKs

Optimized release builds (R8 + baseline profile) of the
[edrock200/silo-android](https://github.com/edrock200/silo-android) fork:
upstream Silo Android 1.0.0, newer upstream fixes, an **HEVC Level Override**
setting, **in-app YouTube trailers**, and a **Trailer button** next to Play. Built from branch `feat/hevc-level-override-v5` at commit 5bd0b7cbd.
See [CHANGELOG.md](CHANGELOG.md).

| File | Device | Version | Version code |
|---|---|---|---|
| [`silo-tv-hevc-release.apk`](silo-tv-hevc-release.apk) | Android TV (NVIDIA Shield, etc.) | 1.0.0-hevc (27) | 220000055 |
| [`silo-phone-hevc-release.apk`](silo-phone-hevc-release.apk) | Phone / tablet | 1.0.0-hevc (27) | 220000054 |

## Direct download

These links always serve the newest build from this branch.

| App | Downloader code | Downloader link | Direct APK |
|---|---:|---|---|
| Android TV | `4536250` | <http://aftv.news/4536250> | <https://github.com/edrock200/silo-android/raw/apk-builds/silo-tv-hevc-release.apk> |
| Phone / tablet | `2364800` | <http://aftv.news/2364800> | <https://github.com/edrock200/silo-android/raw/apk-builds/silo-phone-hevc-release.apk> |

On Android TV / Google TV, install **Downloader by AFTVnews**, enter the code,
then allow APK installs from Downloader when prompted.

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

## Trailers

- Press **Trailer** next to Play on a movie or series page, or pick one from
  the "Trailers & More" row.
- A trailer stored on your Silo server plays first; otherwise the YouTube
  trailer plays inside Silo, with no YouTube profile picker. The button is
  hidden when a title has no trailer or teaser.
- YouTube player remote: OK / Play-Pause pauses and resumes, Left / Right skip
  10 seconds, Back closes. It closes itself when the trailer ends.
- If a trailer's owner blocks playback outside YouTube, Silo opens the YouTube
  app instead. YouTube may show its own ads.
