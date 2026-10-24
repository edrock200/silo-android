---
name: fork-release
description: Sync this fork (edrock200/silo-android) with upstream Silo-Server/silo-android, reapply the HEVC Level Override patch, build optimized release APKs for phone and TV versioned <public>-hevc, and publish them with a changelog to the apk-builds branch. Use when asked to update, rebuild, or release the fork's APKs.
---

# Fork release: sync upstream, patch, build, publish

This fork adds one feature on top of upstream: **HEVC Level Override**. It
also carries a small build fix. Each release rebases those commits onto
upstream `main` and ships optimized APKs for both apps.

## What the fork patch touches

- `shared/.../model/settings/PlaybackSettingsKeys.kt`: `HevcLevelOverride`
  key (`player.hevc_level_override`, local-only Int, normalized level x 30,
  0 = Auto).
- `android-shared/.../settings/PlayerSettingsStore.kt` (default-implemented
  members, so test fakes compile unchanged) and
  `AndroidPlayerSettingsStore.kt` (flow, setter, cleared on "Use Profile
  Settings").
- `android-shared/.../player/MediaCodecCapabilitiesProbe.kt`:
  `hevcLevelOverride` plus `withHevcLevelOverride()`, applied in `probe()` so
  every capability report (playback, downloads, diagnostics) carries it.
- `android-shared/.../settings/HevcLevelOverrideBinder.kt`: mirrors the
  setting into the probe. Started from `SiloApplication` and
  `SiloTvApplication`.
- UI: TV `TvSettingsScreen.kt`/`TvSettingsViewModel.kt` (Streaming group +
  picker); phone `PlaybackSettings.kt` (`PlaybackDecoderSection`),
  `SettingsPages.kt`, `SettingsViewModel.kt`.
- `gradle/verification-metadata.xml`: `also-trust` hashes for regenerated
  Plugin Portal marker POMs (kotlin.android, kotlin.plugin.compose,
  kotlin.plugin.serialization, org.jetbrains.compose). Ask the user before
  trusting any new hash, and check that the marker only points to the
  matching artifact first.

## Steps

1. **Sync.** `git remote add upstream https://github.com/Silo-Server/silo-android`
   (public, so anonymous fetch works), then
   `git fetch --depth=200 upstream main:refs/remotes/upstream/main`. If
   nothing is new since the last release, stop.
2. **Rebase** the fork commits onto `upstream/main` and push them to a **new**
   branch, `feat/hevc-level-override-vN`. Force-pushing an existing branch is
   blocked in this environment.
3. **Version.** Find the public version from the tags:
   `python3 -I scripts/release-version.py beta <newest vX.Y.Z-beta.B tag>`
   prints `version`, `build` and `version_code`. The `v1.0.1-rc.*` tags are
   older than the `1.0.0` builds; trust the script, not tag names. Use
   `siloVersionName=<version>-hevc`, `siloBuildNumber=<build>`, and
   `siloVersionCode=<version_code>`.
4. **Build** release APKs, never debug (debug builds are much slower on TV).
   Build one app at a time; building both together runs out of memory:

   ```sh
   export ANDROID_HOME=/home/user/android-sdk   # install platforms;android-36 + build-tools;36.0.0 if missing
   for t in androidTvApp androidApp; do
     ./gradlew -I <scratchpad>/mirror.gradle :$t:assembleRelease \
       -PsiloVersionName=1.0.0-hevc -PsiloBuildNumber=26 -PsiloVersionCode=110000026 \
       -PallowDebugReleaseSigning=true -PsiloReleaseChannel=sideload \
       -Dorg.gradle.jvmargs="-Xmx6g -XX:MaxMetaspaceSize=1g" \
       -Pkotlin.compiler.execution.strategy=in-process \
       -Dorg.gradle.parallel=false --max-workers=2
   done
   ```

   - `mirror.gradle` is a local init script that swaps
     `repo.maven.apache.org` for
     `https://maven-central.storage-download.googleapis.com/maven2/`, because
     Maven Central answers 429 to this environment. Recreate it in the
     scratchpad and never commit it. Dependency verification still applies.
   - Each app takes about 15 minutes. Run it in the background with a long
     timeout.
   - Never use `pkill -f <pattern>` in the same command as the build: it
     matches its own command line and kills the build.
5. **Verify** each universal APK: `build-tools/36.0.0/aapt2 dump badging <apk>`
   shows `versionName='<version>-hevc'` and the expected versionCode (phone
   `code*2`, TV `code*2+1`).
6. **Changelog.** Add a section to `CHANGELOG.md` on the new branch: the fork
   changes, then `git log --format='- %s' <public tag>..upstream/main`.
7. **Publish** to the orphan `apk-builds` branch (separate clone, no
   force-push): replace the APKs with
   `silo-tv-<version>-hevc-release.apk` and
   `silo-phone-<version>-hevc-release.apk`, copy `CHANGELOG.md`, update its
   `README.md`, commit, and push. Also send both APKs to the user.
   GitHub Releases can't be created from this environment (no `gh` token,
   and the GitHub tools can't upload release files).
