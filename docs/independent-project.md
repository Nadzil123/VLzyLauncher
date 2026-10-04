# Independent VLzyLauncher project

The owner's final clarification on 2026-10-04 is that VLzyLauncher is an
**independent project** and an **unofficial Minecraft: Java Edition launcher**.
Its code provenance is an independently maintained **hard fork of ZalithLauncher2**.
Nadzil123 creates and maintains VLzyLauncher; its own Concept and Specification
govern development, architecture and releases. Original code remains credited to
its authors; VLzyLauncher is not affiliated with Mojang or Microsoft.

The local repository has only the VLzyLauncher `origin` remote and no original-
project synchronization workflow. GitHub's repository relationship could not be
verified while the repository lookup failed; this is separate from VLzy's own
development direction and the recorded origin of its code.

## Project entry points

About shows the VLzy logo, app version, Nadzil123 profile, issue reporting,
contribution instructions and GPL license. Project, release, support and
contribution links use `Nadzil123/VLzyLauncher`. The contribution guide accepts
code, testing, documentation and translation changes through this repository.
Original launcher authors and inherited translation contributors appear in
Acknowledgements, alongside other projects and libraries.

The dormant original-launcher update client, dialogs, metadata/mirror endpoints
and settings have been removed. The Releases button opens the VLzyLauncher
GitHub releases page. No automatic update server is configured.

The automatic sponsorship popup and its successful-game counter have been
removed. Minecraft exit codes still reach the existing exit listener. Old
sponsorship and update preferences can remain inert in existing preference
stores; the change does not reset or delete user data.

## Compatibility and provenance

- Application IDs, signing identity, version, supplied artwork, accounts, game
  directories and migration logic retain the earlier alpha behavior.
- Historical names in `LegacyNames.kt` remain necessary to read existing data
  and support already-installed external plugins.
- `UpstreamReferences.kt` now contains only original-code attribution and the
  actual optional native-plugin provider. Settings identifies those plugin
  downloads as third-party sources.
- Original copyright, bundled library licenses, controller-layout authorship and
  third-party library namespaces remain accurate. See
  [UPSTREAM_NOTICE.md](../UPSTREAM_NOTICE.md).

## Verification

Source audit passed: no old project-owned tracked paths, application namespaces
or bundled class namespaces. Removed updater/sponsor references have no remaining
source consumers. `git diff --check` passed. The first application test run and
ARM64 APK build passed (`BUILD SUCCESSFUL`, 17m 47s; 175 tasks, 28 executed). The
final build with the hard-fork credit and socket correction also passed
(`BUILD SUCCESSFUL`, 17m 14s; 175 tasks, 14 executed).

Final application results: **119 tests, 116 passed, 0 failures/errors, 3 optional
remote tests skipped**. Both package audits passed: all 15 VLzy string resources
match the current source, including the unofficial label, Nadzil123 ownership
and hard-fork credit. Removed updater/sponsor classes, URLs, resource keys and
images are absent. The seven own-project/profile URLs are packaged; actual
original-code and optional-plugin provider URLs remain intact.

Manifest, DEX/native namespace, 21 JNI exports, both bundled LWJGL archives and
their extraction markers passed inspection. The supplied logo remains unchanged
and is packaged at 1254 × 1254. APK signature verification passed with the same
certificate as the earlier VLzy debug build.

```text
APK: VLzyLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-alpha.1-20261003-arm64-v8a.apk
Version: 1.0.0-alpha.1-20261003-debug (200048)
Application ID: com.nadzil123.vlzylauncher.debug
Size: 221127068 bytes
SHA-256: 65a9c639e083557418eb180d1b8a6cc675e08b003aace42cd7cc835c6e7808be
```

Build/test command:

```sh
./gradlew :VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug -Darch=arm64
```

The [source review](reviews/2026-10-04-independent-project-review.md) records the
completed independent review: no confirmed issues, all 18 locale string files
parsed, removed callers checked, and the 12 new English defaults verified.

The final resource rebuild exposed an existing installer UDP restart race, which
is now corrected by waiting for the receive operation to unwind after socket
closure. Five focused socket tests pass, including 200 rapid restart/rebind
cycles and callback-driven shutdown. The final full application suite and APK
include this correction, and its separate independent review found no issues.

Physical-device checks remain: About links and layout, in-place update,
home-screen launch, saved accounts/preferences, plugin selection and a Minecraft
session. Local build checks do not establish these device results.

## Publication

The earlier naming commits through `d46d90dd` were successfully pushed to `main`.
During this follow-up, GitHub returned `Repository not found` for
`Nadzil123/VLzyLauncher`, although `gh auth status` still confirmed an active
Nadzil123 login. The owner has been asked whether the repository URL changed.
No tag, GitHub release or APK upload is implied by the earlier branch push.
