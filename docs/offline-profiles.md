# Offline profiles and deferred integrations

The 2026-10-01 owner request makes offline profiles usable without a Microsoft
account and defers CurseForge and Microsoft sign-in. This direction was recorded
in the living Concept, Specification, and Implementation Plan before code changes,
following `VLzyLauncher_CODEX_HANDOFF.md`.

## Account flow

Open **Accounts → Add account → Offline**, enter a username, and save. The existing
local editor also supports a custom UUID. If Play needs an account, it opens this
editor directly. Offline is the first option in the account menu.

The inherited regional/Microsoft-account prerequisite has been removed. A saved
selected profile is retained, including its UUID. If that selection no longer
exists, the launcher prefers an available local profile, then falls back to the
first remaining account. Existing Microsoft and external-server profiles retain
their account types and authentication behavior.

The existing local login and launch paths are reused: local profiles skip online
session validation and token refresh. They provide a local identity; services and
servers requiring online authentication still require their own credentials.
Launching without internet also requires the game's files to be present locally.

## Availability messages

The current build has neither integration configured. It shows these exact English
messages instead of prompting the user to configure the build:

- **CurseForge is unavailable until further notice.**
- **Microsoft sign-in is unavailable until further notice.**

The Microsoft sign-in menu item is disabled while configuration is absent, with
the notice displayed below it. A restored/direct sign-in entry also shows the
notice. Existing request guards stop unconfigured official API requests before
transport. Public CurseForge CDN URLs remain usable. Modrinth remains the default
content provider when no preference was saved; an older saved CurseForge choice
can be changed in the platform filter. Worlds remain CurseForge-only and are
therefore unavailable through that browser in this build.

The source retains configured integration paths for future work; this follow-up
does not request credentials or promise an availability date. The earlier diagnosis
and future setup reference remain in [service configuration](service-configuration.md).

## Verification

Six account regression tests cover local-only selection, saved identity retention,
missing-selection fallback, existing online-only profiles, and separate local and
online authentication requirements. Before the fallback fix, one test failed for
the expected reason and the other five passed (focused run: 9m 19s).

The initial full run passed: 110 tests, 107 passed, 0 failures/errors, and 3 optional
remote tests skipped; the ARM64 build completed in 18m 16s. Independent review found
one raw-error message in cape refresh for an existing Microsoft profile. The
handler now uses the common account error formatter, and the reviewer confirmed
the correction with no remaining findings. See the [review record](reviews/2026-10-01-offline-profiles-review.md).

The final incremental run includes that correction and passed:

| Check | Result |
| --- | --- |
| Application tests | 110 total: **107 passed, 0 failures/errors, 3 optional remote tests skipped** |
| Account selection/authentication regressions | All six passed, no skips |
| ARM64 debug build | `BUILD SUCCESSFUL`, 8m 1s; 175 tasks, 12 executed |
| APK signature | `apksigner verify` passed |
| APK identity | `com.nadzil123.vlzylauncher.debug`, code `200046`, version `1.0.0-bootstrap.3-debug` |
| Application label / native ABI | `VLzyLauncher` / `arm64-v8a` |
| Availability text | Both exact English sentences verified in valid resource XML |
| Patch formatting | `git diff --check` passed |

The command was `:VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug
--continue -Darch=arm64`, using the external host tools described in
[Bootstrap verification](bootstrap-verification.md). The six account tests extend
the previously verified 104-test application suite. The three remote tests remain
opt-in; no public upload tests were enabled.

APK: `VLzyLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-bootstrap.3-arm64-v8a.apk`
(217,358,986 bytes). SHA-256:

```text
cbf95354b335c3eb28126a87c916b747dbbd9f49cdb5ed64d8979870e0c6e818
```

## Device feedback and remaining checks

On 2026-10-01 the owner reported that the earlier errors were resolved and that
they successfully played Minecraft with this build. The workspace still has no
attached device session; the following detailed scenarios have not all been
independently observed:

1. Without a Microsoft account, use Play to create a local profile and launch an
   already installed game.
2. Restart the launcher and check that the selected username/UUID is retained.
3. Open Add account and confirm Offline is first and the Microsoft notice is visible.
4. Select CurseForge and confirm the availability notice; switch back to Modrinth
   and check a shader or modpack search/download.

Changes are local on `main`; no remote push or APK publication has been made.
