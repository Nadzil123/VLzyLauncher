# Download completion follow-up — 2026-09-30–2026-10-01

This records the earlier `bootstrap.1` artifact. The current build and subsequent
403/400 diagnosis are in [online service configuration](service-configuration.md);
rebuilding replaces the APK in the Gradle output directory.

The user reports that the Bootstrap APK opens, but a download reaches 100% without
becoming usable. The affected Minecraft version, loader, download source and exact
device error are still unknown. No claim that a public server is down has been
verified.

The primary checkout now uses `main`. The existing uncommitted Bootstrap changes
were preserved. New verification runs use this checkout, not the earlier temporary
validation worktree.

## Investigation

- `Fetcher` reports transferred bytes before checksum verification and file commit.
  Retried bytes are included again. `TaskBatch` previously treated that cumulative
  transfer count as installation progress, allowing 100% while files were failing.
- `GameJVMRunner` starts the local exit-code listener before starting the installer
  service, but `JVMSocketServer.start` previously returned before binding its UDP
  port. A fast installer exit could be lost; a bind failure was logged without
  reaching the caller. The caller could then wait up to its 15-minute timeout.
- The initial suspicion that Auto always selected a China mirror was ruled out:
  callers check the region before using that helper. Source selection is unchanged.

## Repair

- Reserve 100% for a successfully completed, verified file batch.
- Bind the installer's local listener before returning from startup; propagate bind
  errors and release the old listener when restarting.
- Keep listener cleanup in `GameJVMRunner`'s `finally` block. The completion callback
  no longer risks closing a subsequent installer's listener.
- Default the launcher to English when no language preference has been saved,
  using the existing picker and Android per-app language API. Preserve explicit
  choices. See [Android's language guidance](https://developer.android.com/guide/topics/resources/app-languages).
- Version: `1.0.0-bootstrap.1`, version code `200044`.

## Verification

The five new regression tests failed against the pre-fix implementation:
checksum failure and fallback reported premature completion, a port bind error
did not reach the caller, and immediate/restarted installer exits timed out.
Tests use local HTTP servers, real temporary files and loopback UDP sockets.

The first full repaired run passed 96 tests with three explicitly skipped remote
tests and built the ARM64 APK. A subsequent review found and resolved the delayed
callback cleanup race. [The reviewer confirmed that correction](reviews/2026-10-01-download-review.md).

The final rerun includes that correction and passed on 2026-10-01:

| Check | Result |
| --- | --- |
| Application tests | 99 total: **96 passed, 0 failures/errors, 3 remote tests skipped** |
| New regression tests | All five passed |
| ARM64 debug build | `BUILD SUCCESSFUL`, 9m 50s; 175 tasks, 12 executed |
| APK signature | `apksigner verify` passed |
| APK identity | `com.nadzil123.vlzylauncher.debug`, version code `200044`, label `VLzyLauncher` |
| Native ABI | `arm64-v8a` |
| Original specification and plan | SHA-256 values match the initial Bootstrap verification |
| Patch formatting | `git diff --check` passed |

Command, with the same external host-tool setup documented in
[Bootstrap verification](bootstrap-verification.md):

```sh
./gradlew :VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug --continue -Darch=arm64
```

APK: `VLzyLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-bootstrap.1-arm64-v8a.apk`
(216,329,961 bytes). SHA-256:

```text
3a6a9d3a16b86d074e0f3b6196ce40f3ba0f4c3ba1ffe2c497e495b44f92c8c2
```

The changes remain local and uncommitted on `main`; no remote push or publication
was performed. The original device report still needs a retry with this APK and
its exact version/source/error if the issue persists. These checks establish the
reproduced client fixes, not an outage diagnosis or a successful device install.
