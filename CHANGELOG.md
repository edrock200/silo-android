# Changelog — edrock200/silo-android fork

This fork tracks [Silo-Server/silo-android](https://github.com/Silo-Server/silo-android)
and adds an HEVC level override. Fork builds use the upstream public version
with a `-hevc` suffix. APKs are on the
[`apk-builds`](https://github.com/edrock200/silo-android/tree/apk-builds) branch.

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
  - TV: Settings → Streaming → HEVC Level Override.
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
