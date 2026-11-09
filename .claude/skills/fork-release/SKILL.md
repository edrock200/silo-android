---
name: fork-release
description: Sync this fork (edrock200/silo-android) with upstream Silo-Server/silo-android, reapply the fork patches (HEVC Level Override, in-app trailers, Trailer button), build optimized release APKs for phone and TV versioned <public>-hevc, and publish them with a changelog to the apk-builds branch. Use when asked to update, rebuild, or release the fork's APKs.
---

# Fork release: sync upstream, patch, build, publish

This fork adds three features on top of upstream: **HEVC Level Override**
(TV: Settings → Playback → Streaming; phone: Settings → Playback → Decoder),
**in-app YouTube trailers**, and a **Trailer button** next to Play. Each
release rebases those commits onto upstream `main` and ships optimized APKs
for both apps.

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
- `gradle/verification-metadata.xml`: no fork changes. Upstream adopted the
  Plugin Portal marker-POM hashes the fork used to carry (#757). If dependency
  verification fails again, ask the user before trusting any new hash, and
  check that the marker only points to the matching artifact first.

- In-app YouTube trailers: `android-shared/.../ui/YoutubeTrailerActivity.kt`
  (WebView + YouTube IFrame API, served from base URL
  `https://www.siloserver.org` because the embed rejects players without an
  HTTPS origin/referrer), `YoutubeTrailerLauncher.kt` (`openYoutubeTrailer`
  tries it first, `openYoutubeTrailerExternally` is the old handoff), and an
  `<activity>` entry in both app manifests. If an upstream rebase conflicts
  in `YoutubeTrailerLauncher.kt`, keep upstream's external handoff order
  inside `openYoutubeTrailerExternally`.
- Trailer button next to Play: `primaryTrailer()` in
  `shared/.../model/catalog/TrailerRail.kt`; TV `HeroActionRow`
  (`onPlayTrailer`) in `TvItemDetailScreen.kt`; phone `HeroActionStack`
  (`onPlayTrailer`) in `DetailSharedComponents.kt`, fed by
  `rememberHeroTrailerAction()` in `TrailersRail.kt` from
  `MovieDetailContent.kt` and `SeriesDetailContent.kt`.

- Fixes to upstream AI subtitle code (drop each one if upstream fixes it,
  and resolve rebase conflicts in upstream's favor when it does):
  `TvPlayerViewModel.aiAudioSources()` (TV "From audio" uses server audio
  ordinals) and the AI-completion replan fallback in `refreshSubtitles()`;
  the 60-second cooldown on the uncertain-request fence in
  `shared/.../network/apiv2/SubtitleAiCreateV2Api.kt`; normalized language
  match in phone `SubtitleAiSupport.transcribeKindFor()`.

- TV microphone permission for keyboard voice typing in Search:
  `RECORD_AUDIO` + optional `android.hardware.microphone` in the TV manifest,
  and `RequestMicrophonePermissionOnce()` (`androidTvApp/.../search/TvMicrophonePermission.kt`)
  called at the top of `TvSearchScreen`.

- Live AI subtitles on TV: `shared/.../playback/PlaybackLiveSubtitles.kt`
  (decodes `subtitle_translation_started|cues|completed|failed` realtime
  events; test `PlaybackLiveSubtitlesTest`), `TvPlaybackRealtimeController`
  (routes them), `TvPlayerViewModel.applyLiveSubtitleEvent` / `liveSubtitle` /
  `clearLiveSubtitle` (cleared when the AI job poll finishes),
  `TvLiveSubtitleOverlay.kt`, and two hooks in `TvPlayerScreen` (hide Media3's
  subtitle view while live, draw the overlay). Drop it if upstream adds live
  cues itself.

- Admin console (phone and TV): generic screens built from the bundled spec
  `android-shared/src/androidMain/assets/admin/openapi-admin.json`, made by
  `python3 -I scripts/admin-spec.py` from silo-server's
  `contracts/api/v2/openapi.json` (operations under `/api/v2/admin` or marked
  `x-silo-class: acting_admin`). Regenerate it when syncing if the server's
  admin API changed. Engine in `shared/.../admin/` (`AdminSpec`,
  `AdminCatalog`, `AdminForm`, `AdminDisplay`; test `AdminEngineTest`) and
  `shared/.../network/apiv2/AdminV2Api.kt` (registered in `NetworkModule`).
  `android-shared/.../common/admin/AdminConsoleViewModel.kt` (spec loader,
  `AdminAccess`, the console's own back stack; test `AdminBundledSpecTest`)
  and `common/di/AdminModule.kt` (added to both apps' Koin modules). Phone:
  `screens/admin/AdminConsoleScreen.kt`, `Route.AdminConsole`,
  `LocalAdminConsole` (provided in `AppNavigation`, read by `ProfileMenu`).
  TV: `screens/admin/TvAdminConsoleScreen.kt`, `TvRoute.AdminConsole`,
  `onOpenAdmin` + `showAdmin` in `TvMainShell`'s profile dropdown.
- Hand-built admin screens (owner decision: hand built and kept up to date
  with the server): dashboard + now playing, request moderation, tasks and
  scans, logs. Typed models in `shared/.../admin/AdminModels.kt`; the
  operation ids they call in `AdminOps`, `AdminService` and the four view
  models in `android-shared/.../common/admin/AdminScreensViewModels.kt`;
  labels in `AdminPresentation.kt` (`AdminText`). Phone UI
  `screens/admin/AdminHandBuiltScreens.kt`; TV UI
  `screens/admin/TvAdminHandBuiltScreens.kt` (focus via
  `ReclaimPageFocus`, never `runCatching { requestFocus() }`).
  `AdminHandBuiltContractTest` checks every operation, field, query param
  and request body these screens use against the bundled spec.

## Steps

1. **Sync.** `git remote add upstream https://github.com/Silo-Server/silo-android`
   (public, so anonymous fetch works), then
   `git fetch --depth=200 upstream main:refs/remotes/upstream/main`. If
   nothing is new since the last release, stop.
   **Admin API.** Also refresh the server: in the read-only clone
   `/home/user/silo-server` (or `git clone --depth=1
   https://github.com/Silo-Server/silo-server`), `git pull`, then run
   `python3 -I scripts/admin-spec.py <silo-server>/contracts/api/v2/openapi.json`
   in this repo. If the asset changed:
   - run `./gradlew :android-shared:testDebugUnitTest --tests 'org.siloserver.silo.common.admin.*'`
     and `:shared:testDebugUnitTest --tests 'org.siloserver.silo.admin.*'`;
   - when `AdminHandBuiltContractTest` fails, it names the operation or field
     that changed. Update the model in `AdminModels.kt`, the view model and
     both screens to match (look at the server's handler under
     `internal/apiv2/admin_*.go` and the web admin page under
     `web/src/pages/Admin*.tsx` for the new meaning), and keep the test's
     expectations in step;
   - when the server adds an admin feature the hand-built screens should
     show (a new dashboard number, a new request action, a new log filter),
     add it to both phone and TV, and note it in the changelog;
   - the generic console picks up everything else from the new asset with
     no code change.
   A spec change alone is a reason to publish a build even when upstream
   Android has nothing new.
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
       -PsiloVersionName=<version>-hevc -PsiloBuildNumber=<build> -PsiloVersionCode=<version_code> \
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
     Its full contents:

     ```groovy
     def mirror = { RepositoryHandler repos ->
         repos.all { r ->
             if (r instanceof MavenArtifactRepository && r.url.toString().startsWith("https://repo.maven.apache.org")) {
                 r.url = "https://maven-central.storage-download.googleapis.com/maven2/"
             }
         }
     }
     settingsEvaluated { s -> mirror(s.pluginManagement.repositories); mirror(s.dependencyResolutionManagement.repositories) }
     allprojects { mirror(buildscript.repositories); mirror(repositories) }
     ```
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
   force-push): overwrite `silo-tv-hevc-release.apk` and
   `silo-phone-hevc-release.apk`. Keep these names: the aftv.news Downloader
   codes (TV `4536250`, phone `2364800`) in its README and in this repo's
   README point at
   `https://github.com/edrock200/silo-android/raw/apk-builds/<name>`, so
   renaming breaks them. Copy `CHANGELOG.md`, update the version table in its
   `README.md` (leave the Downloader codes as they are), commit, and push.
   Then check that each link returns HTTP 200 with the new size, and send both
   APKs to the user. New Downloader codes can only be made by the user at
   go.aftvnews.com, which requires a captcha.
   GitHub Releases can't be created from this environment (no `gh` token,
   and the GitHub tools can't upload release files).
8. **Announce on Discord** after every new published build. Post to the
   webhook in `$SILO_DISCORD_WEBHOOK`. Never write the webhook URL into the
   repo, a commit, or a log: anyone with it can post to the channel. The
   owner also keeps it as the GitHub repository secret `SILO_DISCORD_WEBHOOK`,
   but repository secrets reach only GitHub Actions, not cloud sessions. If
   the variable is unset, ask the user to add `SILO_DISCORD_WEBHOOK` as an
   environment variable in the
   cloud environment's settings (environment menu in the session title bar →
   Edit), and don't ask them to paste it into the chat.
   - Content, in plain language for non-technical users: the version, what's
     new or fixed (from this release's `CHANGELOG.md` section, rewritten
     without commit jargon or PR numbers), how to update, and the downloads:
     TV Downloader code `4536250` (<http://aftv.news/4536250>) and phone code
     `2364800` (<http://aftv.news/2364800>), plus the direct APK links.
   - Build the JSON with Python's `json` module (not hand-escaped shell),
     keep `content` under 2000 characters (Discord's limit), and send it with
     `curl -sS -H 'Content-Type: application/json' -d @payload.json "$SILO_DISCORD_WEBHOOK"`.
     Expect HTTP 204, and tell the user the post went out.
