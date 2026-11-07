# Changelog — edrock200/silo-android fork

This fork tracks [Silo-Server/silo-android](https://github.com/Silo-Server/silo-android)
and adds an HEVC level override, in-app YouTube trailers, and a Trailer
button. Fork builds use the upstream public version with a `-hevc` suffix.
APKs are on the
[`apk-builds`](https://github.com/edrock200/silo-android/tree/apk-builds) branch.

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-07 (live AI subtitles)

### Added in this fork

- **TV shows AI subtitles while they're being made.** An AI translation or
  transcription takes several minutes to finish, and the TV used to show
  nothing until the whole file was done, while the web player showed lines
  within seconds. The server already streams the translated lines to the
  player as it works, starting at the viewer's position; the Android app
  ignored them. The TV player now draws those lines over the picture as they
  arrive (showing "Translating subtitles…" until the first ones come), then
  switches to the finished subtitle track when it's ready. The phone app
  doesn't have this yet.

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-06 (afternoon sync)

Based on upstream `main` at `f13a2652a`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(phone): stop sort and filter pills looking switched on (#835)
- fix(phone): tell TalkBack which For You list pill is chosen (#834)

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-06 (morning sync)

Based on upstream `main` at `ee178d887`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged. Upstream
brought back an on-screen voice search button on TV (#830), which uses the
system speech recognizer and needs no permission; the fork's microphone
request stays for keyboards whose own mic key needs it.

### Upstream changes since the previous build

- feat(tv): bring back the voice search button on Android TV search (#830)
- feat(requests): let users remove their own failed requests (#792)
- fix(membership): show a dismissable dialog when an offline change isn't confirmed (#827)
- fix(detail): show "Translating description…" for the shown episode on series pages (#822)
- fix(phone): show which For You list is chosen in the pill row (#829)
- fix(tv): close the audiobook Speed panel with Back (#705)

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-06

Based on upstream `main` at `e9861fa66`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(audiobook): start a fresh session when the server expired the old one (#708)

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-05 (afternoon sync)

Based on upstream `main` at `fee491916`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(phone): give every tab the same header and first-row position (#815)
- fix(tv): return focus to a Continue Watching show after its episode advances (#814)

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-05 (morning sync)

Based on upstream `main` at `670f5ba0a`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- feat(pairing): sign an Android TV in to several servers and accounts from a phone (#810)
- feat(overlays): draw the combined resolution, HDR and audio poster badge (#816)
- fix(tv): stop Home flashing the text title before its logo (#813)
- fix(phone): move the cast bar beside the minimized Home control (#811)

## 1.0.0-hevc (build 29, rebuilt) — 2026-11-05 (microphone)

### Added in this fork

- **TV asks for microphone access for voice typing in Search.** The search
  box's voice input comes from the on-screen keyboard's mic key, and some
  Android TV keyboards only listen when the app holding the text field has
  microphone access. Silo didn't declare it, so the mic key did nothing. The
  TV app now declares the microphone (optional hardware, so TVs without one
  can still install it) and asks once the first time you open Search. If you
  said no, turn it on under Android Settings → Apps → Silo → Permissions.

## 1.0.0-hevc (build 29) — 2026-11-05

Based on upstream `main` at `d65b27cec`, after the public 1.0.0 build 29
(`v1.0.0-beta.29`). Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(playback): allow audio passthrough on Android 9 and older HDMI devices (#778)
- fix(tv): move the series page to the next episode after you finish one (#802)
- fix(tv): make the remote's Stop key leave the player (#809)
- refactor(phone): show Watchlist, Favorites and History in the library grid (#808)
- test(tv): wait for view model work to stop before resetting Main (#806)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-04 (afternoon sync)

Based on upstream `main` at `623bb3513`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged; the HEVC
Level Override now follows upstream's per-account settings files like every
other local playback setting.

### Upstream changes since the previous build

- feat(accounts): sign in to more than one account per server (#795)
- fix(tv): keep focus on a Continue Watching card when its next episode replaces it (#803)
- fix(tv): keep the marquee detail line whole while it fades (#805)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-04

Based on upstream `main` at `64ae5f08c`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- feat(calendar): show DIGITAL and PHYSICAL badges on movie releases (#722)
- feat(tv): add a clear button to the search field (#798)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-03 (afternoon sync)

Based on upstream `main` at `6f3e36820`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(box-sets): stop grouping box sets in library grids (#796)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-03

Based on upstream `main` at `65664d7ce`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(auth): let an account with a temporary password choose its own (#790)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-02 (afternoon sync)

Based on upstream `main` at `7e4580db1`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(player): show the audio output the sink chose, not the plan's guess (#789)
- fix(tv): drop Watchlist and Favorites from the profile menu (#786)
- test(tv): hold the replan answer until the subtitle switch mounts (#788)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-02 (morning sync)

Based on upstream `main` at `4c0a2178c`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(tv): move Requests from the top bar to the profile menu (#782)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-02

Based on upstream `main` at `51d4f9013`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- feat(box-sets): browse, open and search movie box sets (#646)
- feat(playback): draw embedded subtitles from the stream and switch them without a remount (#770)
- fix(player): don't offer or auto-pick subtitles the server can't show (#776)
- fix(tv): outline focused cards in white with a small lift, as on tvOS (#781)
- fix(tv): read only the Watch Next rows instead of all of Home (#775)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-01 (morning sync)

Based on upstream `main` at `864cd1d6f`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(search): stop listing in-library titles under Available to request (#773)

## 1.0.0-hevc (build 28, rebuilt) — 2026-11-01

Based on upstream `main` at `77e316ff5`. No new public upstream build, so
the version is unchanged. Upstream now trusts the same Gradle Plugin Portal
marker POMs the fork patched (#757), so the fork's build fix is retired.
The other fork changes are carried over unchanged.

### Upstream changes since the previous build

- feat(tv): tell nearby phones a TV is signing in, in release builds too (#747)
- fix(tv): ask to open a profile automatically the way Apple TV does (#762)
- fix(tv): let Sign Out keep or remove the server, as on Apple TV (#764)
- fix(tv): give the server address screen Apple TV's Advanced options and errors (#758)
- fix(tv): match Apple TV's sign-in states for new, declined and wrong-password (#754)
- fix(tv): forget a server the TV never signed in to when you change server (#759)
- fix(tv): outline the username field when the username is missing (#760)
- fix(tv): say "Could not reach a Silo server" when the address answers an HTTP error with no message (#771)
- fix(tv): name the TV by its device name when it asks to sign in (#746)
- fix(tv): word the phone setup panel like Apple TV's (#761)
- fix(tv): withdraw the sign-in request when a phone abandons setup (#752)
- fix(tv): drop a silent phone after three minutes of phone setup (#748)
- fix(pairing): stop the phone approving a TV that cancelled sign-in (#749)
- fix(pairing): word phone setup failures for people, not the server (#753)
- fix(pairing): say "tablet" on the TV setup card on a tablet (#755)
- fix(player): offer skip for every marker occurrence (#694)
- build: trust the Gradle Plugin Portal's own plugin marker POMs (#757)
- docs(license): add a platform library exception to the AGPL (#772)

## 1.0.0-hevc (build 28, rebuilt) — 2026-10-31

Based on upstream `main` at `76dee90ad`. No new public upstream build, so
the version is unchanged. Fork changes are carried over unchanged.

### Upstream changes since the previous build

- fix(tv): keep subtitles off when Auto or Off resolves to none at start (#745)
- feat(detail): let Back leave a More Like This chain in two steps (#737)

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
