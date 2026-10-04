# Independent project identity review — 2026-10-04

Base: `d46d90ddc309155b4775110707b3e6f65baba32d`.
Scope: About/project ownership, hard-fork credit, retirement of the dormant
original-launcher updater and sponsorship prompt, related resources and docs.

## Review method and findings

The completed independent review found no confirmed
Critical, Important or Minor issues. It parsed all 18 locale string files,
checked for duplicate/retired keys and default-English coverage, and traced the
removed updater and game-exit call sites. The implementer's review agreed.

- About puts Nadzil123 in the project creator/maintainer card. Original code is
  separately credited to ZalithLauncher2, MovTery and contributors. The final
  credit uses the owner's term `independently maintained hard fork`.
- Project, releases, issues, contribution, license and contributor links use
  the VLzy repository. Creator profile navigation uses Nadzil123. Each new row
  reuses the existing wrapping text and link-button component.
- The old updater's view model, dialogs, metadata models and exception have no
  surviving callers. Manual release navigation still handles the existing
  `CheckUpdate` event. No startup fetch of original-project APK metadata remains.
- Removing the sponsorship counter leaves game termination forwarding directly
  to the same `(Int, Boolean) -> Unit` exit listener. Modpack/control import
  handling still runs at activity creation.
- Removing obsolete settings declarations leaves their persisted values inert;
  no preference store, account, game directory or migration data is deleted.
- Removed creator and old translation-service resource names have no remaining
  callers or locale definitions. The 12 new English strings supply fallback text for
  locales without translations; the fixed creator handle is non-translatable.
- Native-plugin downloads retain their actual third-party provider, clearly
  labelled in both empty and populated settings states. Existing plugin aliases
  and old-storage migration keys remain compatible.
- No copyright notices, bundled license text, app IDs, signing configuration or
  supplied logo are changed by the identity slice. A later installer socket
  correction discovered during verification is recorded separately below.

## Verification and limits

The first application test/build run passed (`BUILD SUCCESSFUL`, 17m 47s; 175
tasks, 28 executed). The final hard-fork credit then changed one string resource;
the final build and packaged-resource evidence are recorded in
[independent-project.md](../independent-project.md).

`git diff --check` and the source/archive namespace audit passed. Existing tests
cover download resumption, installer socket startup, account selection and legacy
storage migration. No new tests were added merely to repeat UI text or constant
URLs.

Physical-device About layout/link interactions, upgrade behavior and gameplay
remain unverified. Remote repository availability and GitHub fork-network
metadata could not be verified because the authenticated lookup returned
`Repository not found`; local code cannot resolve that service-side condition.

The wider 1.0.0 roadmap and an automatic VLzy update server are outside this
bounded identity change. The current app continues to use supported third-party
Minecraft/content/plugin services as described in the living documents.

## Verification-discovered installer race

The final resource rebuild exposed a pre-existing failure in the unchanged
`JVMSocketServer`: its restart test threw `BindException` on the second bind.
The full run had 117 tests, 1 failure and 3 skips. A separate Java 25 UDP probe
failed 5 of 200 immediate rebinds after `close()`, and 0 of 200 when it waited for
the receiving thread to exit. This matches the deferred close documented in the
[OpenJDK 25 implementation](https://github.com/openjdk/jdk/blob/jdk-25-ga/src/java.base/share/classes/sun/nio/ch/DatagramChannelImpl.java#L1589).

The correction closes the listener and then synchronizes with only the blocked
receive operation before returning from stop. Callbacks stay outside that lock,
so a callback can stop its own server. Exclusive port binding is retained.

Two additional tests cover 200 rapid stop/rebind cycles and callback-driven
shutdown. The original full-suite restart failure supplies the pre-fix evidence;
the extra tests also passed in isolation before the fix, so they are not claimed
as deterministic reproducers. After the fix, all five focused socket tests
passed. A narrow independent follow-up review found no lock-order cycle,
callback deadlock or port-exclusivity regression and no confirmed issues. Final
full-suite and package results are recorded in the verification report.

Final verification passed: 119 application tests, 116 passed, 0 failures/errors,
3 optional remote skips; ARM64 APK build successful in 17m 14s. The APK ownership,
retired-code/endpoint, namespace, runtime, logo and signing audits all passed.
