# Changelog — edrock200/silo-android fork

This fork tracks [Silo-Server/silo-android](https://github.com/Silo-Server/silo-android)
and adds an HEVC level override, in-app YouTube trailers, and a Trailer
button. Fork builds use the upstream public version with a `-hevc` suffix.
APKs are on the
[`apk-builds`](https://github.com/edrock200/silo-android/tree/apk-builds) branch.

## 1.0.0-hevc (build 28) — 2026-10-30

Based on upstream `main` at `8fff98ba3`, after the public 1.0.0 build 28
(`v1.0.0-beta.28`). Fork changes are carried over. Upstream's AI subtitle
session change (#726) and the fork's 60-second retry for uncertain
translation requests touched the same code; both are kept.

### Upstream changes since the previous build

- fix(player): attach AI subtitle jobs to the playback session (#726)
- fix(player): show the AI translate menu as a corner card in landscape (#728)
- fix(player): wait 30 seconds before following a network change mid-playback (#729)
- fix(tv): keep focus reachable after scrolling Search results (#736)
- docs: make the beta Downloader codes the default until a store release (#724)

## 1.0.0-hevc (build 27, rebuilt) — 2026-10-29

Based on upstream `main` at `3cecc7a66`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(tv): start Up Next episodes at 0:00 instead of the previous episode's position (#718)
- fix(tv): run a phone's handoff at the TV's own server address (#716)
- fix(tv): advertise the server's identity on TVs not paired from a phone (#717)

## 1.0.0-hevc (build 27, rebuilt) — 2026-10-28 (evening sync)

Based on upstream `main` at `bda6cbdd7`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- feat(sections): cap Home and library rows at 20 cards and open each row as a full page (#688)
- fix(tv): keep match-content output at or above the video's resolution (#713)
- fix(catalog): build boolean filter rules as JSON booleans (#703)
- fix(tv): let the D-pad leave the filter value search field (#704)
- fix(tv): say when marking a card watched fails (#706)

## 1.0.0-hevc (build 27, rebuilt) — 2026-10-28 (afternoon sync)

Based on upstream `main` at `4d489a823`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- feat(requests): show the server's Discover rows with See all pages (#709)
- feat(artwork): draw episode heroes from the series backdrop (#711)

## 1.0.0-hevc (build 27, rebuilt) — 2026-10-28 (review fixes)

Fixes from a code review of the fork's changes. No upstream changes.

- **Trailer player:** if YouTube reports an error after a trailer has
  started, the player now closes instead of opening the YouTube app (which
  brought back its profile picker). The start-up allowance now counts from
  when the player page loads (20 seconds, with a 45-second backstop), so slow
  connections no longer get sent to the YouTube app. A trailer paused by an
  interruption (notification shade, multi-window) resumes when you return.
- **HEVC Level Override:** the setting keeps working after a failed settings
  read instead of freezing at its last value until the app restarts. Level
  labels no longer change digits in some languages.
- **Phone:** when a title can be downloaded and has a More menu, the Trailer
  button moves into More ("Play Trailer") so the button row isn't crowded.
- **Docs:** the TV setting is under Settings → Playback → Streaming, not
  Settings → Streaming.
- **AI subtitles (fixes to upstream code, found in a code review):**
  - TV "From audio" now transcribes the audio track you pick. When the
    server remuxed or converted the video, the TV always sent the first audio
    track, so a dubbed track was transcribed in the original language.
  - A brief network drop while starting a translation no longer blocks that
    translation until the app restarts. You can retry after a minute; the
    server reuses the job if it had already started.
  - On TV, if the "subtitle ready" live update is missed, Silo refreshes the
    subtitle list after a few seconds, so a finished translation shows up.
  - Phone no longer runs an extra English → English translation (using AI
    credit) when the audio is tagged "eng" and the target is English.

## 1.0.0-hevc (build 27, rebuilt) — 2026-10-28

Based on upstream `main` at `548617cf6`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the 2026-10-27 build

- fix(catalog): send boolean filter values as JSON booleans (#702)

## 1.0.0-hevc (build 27, rebuilt) — 2026-10-27

Based on upstream `main` at `e9c410096`, 4 commits after the public 1.0.0
build 27. Upstream hasn't tagged a new public build, so the version is
unchanged. Fork changes are carried over unchanged.

### Upstream changes since the 2026-10-26 build

- feat(player): show the server's seek-bar previews while scrubbing (#695)
- fix(player): show one centered loading spinner on phone and TV (#696)
- feat(settings): retire the media card caption choice (#700)
- docs(calendar): update the badge label comments (#698)

## 1.0.0-hevc (build 27) — 2026-10-26

Based on upstream `main` at `3627b86c2`, the public 1.0.0 build 27
(`v1.0.0-beta.27`). All fork changes from build 26 below are carried over.

### Changed in this fork

- The Trailer button now uses a screen-with-play icon. It used the same film
  icon as the TV Version selector.

### Upstream changes since build 26

- feat(silocast): control a Roku from the Android phone (#686)
- feat(pairing): sign in a Roku from the Android phone (#685)
- feat(player): remember audio and subtitle choices for the whole series (#680)
- fix(silocast): drop Connect by Address from the remote picker (#687)
- fix(player): adopt a quality change that moves to a version without subtitles (#684)
- test(pairing): hold the connect open in the abandoned-connect test (#690)

## 1.0.0-hevc (build 26) — 2026-10-24

Based on upstream `main` at `51e480a4`, 23 commits after the public
1.0.0 build (`v1.0.0-beta.26`).

### Added in this fork

- **HEVC Level Override** (phone and TV). Silo used to transcode files tagged
  with a higher HEVC level than the decoder advertises, such as HEVC Main 10
  level 6.2 on an NVIDIA Shield, which reports level 5.1, even when the
  decoder plays them fine. The new setting reports a chosen level (Auto, 5.0,
  5.1, 5.2, 6.0, 6.1 or 6.2) to the server so those files direct play, like
  Plex's "ignore level" option. Files that truly exceed the decoder may
  stutter or fail.
  - TV: Settings → Playback → Streaming → HEVC Level Override.
  - Phone: Settings → Playback → Decoder → HEVC Level Override.
  - Stored per profile on the device. Default is Auto, which reports what
    the decoder reports.
- **YouTube trailers play inside Silo** (phone and TV, added in the
  2026-10-25 rebuild). Handing a trailer to the YouTube app could leave you
  on YouTube's "Who's watching?" profile picker, so the trailer never
  played. Trailers now open in a full-screen player that uses YouTube's
  embedded player, which needs no account.
  - Remote: OK / Play-Pause toggles, Left / Right seek 10 seconds, Back
    closes. The player closes itself when the trailer ends.
  - If the trailer's owner blocks embedding, the video is unavailable, or the
    player doesn't start within 15 seconds, Silo hands the trailer to the
    YouTube app as before.
  - YouTube may show its own ads in the embedded player.
- **Trailer button next to Play** (phone and TV, added in the 2026-10-25
  rebuild). Movie and series detail pages get a Trailer button that plays
  the main trailer without scrolling to the "Trailers & More" rail. It
  prefers a local trailer, then a YouTube trailer, then a teaser, and is
  hidden when the item has none.
  - TV: in the action row, after Play / Start Over.
  - Phone: first in the row of round buttons under Play.
- **Build fix:** the Gradle Plugin Portal now serves different bytes for four
  Kotlin/Compose plugin marker POMs, which broke dependency verification on a
  clean machine. The new hashes are trusted alongside the old ones. Each
  marker only points to the matching JetBrains plugin artifact, which is
  still verified.

### Upstream changes since public 1.0.0 (build 26)

- feat(themes): play local theme music on detail pages (#678)
- feat(playback): lower Auto quality when playback keeps buffering, and test connection speed (#636)
- feat(tv): send the playing title's poster and synopsis in remote state (#660)
- feat(tv): keep several Remote Control controllers connected to one TV (#653)
- feat(cast): let the phone share a TV with other controllers and ask before replacing (#652)
- feat(detail): show extra thumbnails in the trailers rail (#641)
- feat(detail): list local extras before YouTube videos (#640)
- feat(episodes): give every episode card the iPhone caption (#644)
- fix(push): stop pushes from a removed server on Android (#657)
- fix(watch-party): cap the in-place seek lead at the host band (#618)
- fix(tv): send YouTube trailers to the TV app's watch page link (#600)
- fix(tv): refresh For You when the tab is shown again (#676)
- fix(pairing): let a phone set up an Android TV on a plain-HTTP server (#666)
- fix(downloads): keep a download going after the app is force-stopped (#675)
- fix(downloads): delete downloads an administrator revoked (#673)
- fix(pairing): keep the TV's pairing key after an iPhone connects (#665)
- fix(tv): show the sign-in code in large digits without boxes (#656)
- fix(tv): grant a handoff only for the profile it prepared (#655)
- fix(player): check the quality row the server says the plan delivers (#649)
- fix(tv): show air dates on the stored day in every time zone (#639)
- fix(home): let phones remove Next Up cards from Home (#632)
- test(cast): add adb hooks to inspect and inject Remote Control targets (#651)
- docs: let projects that work with Silo use the logo and icon (#672)
