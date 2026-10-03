# VLzy Bootstrap Implementation Plan

> **For agentic workers:** Use superpowers:executing-plans to implement this approved Bootstrap slice task-by-task.

**Goal:** Establish a branded, attributed VLzy fork, external release signing, and tested core models while retaining the existing launch machinery.

**Architecture:** Preserve modules and the internal namespace. Introduce Android-independent Kotlin types in `com.nadzil123.vlzylauncher.vlzy`. Reuse Compose Home and About for attribution.

**Tech Stack:** Kotlin/JVM 17, Compose, Gradle 9.5.0, AGP 9.3.0, JUnit 4, GitHub Actions.

**Spec:** `VLzyLauncher_Specification_1.0.md` and `VLzyLauncher_Repository_Gap_Analysis_Implementation_Plan_1.0.md` (sections 4, 5, 20).

## Global Constraints

- Preserve → Wrap → Replace → Remove old path.
- User-facing identity: VLzyLauncher / VeryLazy Launcher.
- Preserve upstream GPL/copyright notices, bundled Java runtimes, renderers, controls and game paths.
- No user-data migration, module extraction or replacement launch engine in Bootstrap.
- No committed release signing credentials.

## Review Focus

- Abbreviated/malformed UUID input must be rejected: Task 3 parsing tests.
- UUID case must not produce distinct identities: Task 3 normalization tests.
- Errors block compatibility; warnings do not: Task 3 result tests.
- Caller changes must not mutate a captured compatibility result: Task 3 snapshot test.
- Debug needs no release secrets; incomplete release signing must fail before producing a release: Task 2 validation and CI gates.

## Baseline and execution

- Base: `b6f0e68966821445607a8819e61ba981f2a55bbb`; original implementation branch `codex/vlzy-bootstrap`.
- Original validation worktree: `/tmp/vlzy-bootstrap-20260929`. Since 2026-09-30, the primary checkout at `/home/Axelly/Downloads/VLzyLauncher` uses `main` at the user's request, with all local changes preserved.
- The supplied specification and implementation plan, and the instruction to proceed with Bootstrap, authorize this slice. Execute inline with one independent final review.
- Run `:VLzyLauncher:testDebugUnitTest` and `:VLzyLauncher:assembleDebug -Darch=arm64`; separate environment blockers from test failures.
- Keep a progress ledger and record baseline/final verification in `docs/bootstrap-verification.md`.

### Task 1: Branding and attribution

**Files:** project/app Gradle files, manifest, `path/UrlManager.kt`, Home/About UI, default/Indonesian strings, README variants.

**Interfaces:** Retain BuildKeys names, internal packages and JNI symbols. Use `VLzyLauncher`, short name `VLzy`, and `https://github.com/Nadzil123/VLzyLauncher`. Use Android application ID `com.nadzil123.vlzylauncher` for side-by-side installation; private-data migration remains future work.

- [x] Apply project/app identity and Bootstrap version, retaining version-code monotonicity.
- [x] Display `VLzyLauncher is an unofficial modified version based on ZalithLauncher2.` on Home and About; retain explicit upstream authorship.
- [x] Route project/update actions to VLzy without advertising upstream APKs as VLzy updates.
- [x] Document build/signing and preserve upstream acknowledgements.
- [x] Validate resources and manifest through the Android build when available; inspect final APK metadata.

### Task 2: Signing and CI

**Files:** `VLzyLauncher/build.gradle.kts`, module properties, `.gitignore`, build/push/release workflows, signing documentation, inherited keystores.

**Interfaces:** External `VLZY_RELEASE_STORE_FILE`, `VLZY_RELEASE_STORE_PASSWORD`, `VLZY_RELEASE_KEY_ALIAS`, `VLZY_RELEASE_KEY_PASSWORD`, or matching `vlzyRelease*` Gradle properties. Debug uses the local Android development key. Release CI decodes `VLZY_RELEASE_KEYSTORE_BASE64`.

- [x] Remove inherited tracked signing keys and committed passwords; keep historical-exposure guidance in documentation.
- [x] Configure independent debug signing and release-only input validation.
- [x] Add release-only CI key handling/cleanup, an explicit manual build variant and a unit-test gate.
- [x] Verify debug without release credentials and rejection of missing/partial release configuration; never print secret values.

### Task 3: Core model foundation

**Files:** `vlzy/model/InstanceId.kt`, `vlzy/compatibility/{CompatibilitySeverity,CompatibilityReason,CompatibilityResult}.kt`, matching JUnit tests.

**Interfaces:** `InstanceId.random()`, `InstanceId.parse(String)` and canonical UUID `toString()`. Reasons contain nonblank `code`/`message` and severity. `CompatibilityResult(reasons)` snapshots completed evaluation evidence and derives `isCompatible`; ERROR blocks, WARNING/INFO do not. This type does not evaluate unknown components by itself.

- [x] Write UUID tests for normalization, equality/round-trip, malformed/abbreviated strings and generated IDs; observe RED.
- [x] Implement the UUID wrapper; observe GREEN.
- [x] Write result tests for error/warning/empty evaluations, reason validation and defensive copying; observe RED.
- [x] Implement minimal pure models; observe GREEN.
- [x] Run the application suite and ARM64 build. Result: 91 passed, 3 explicitly skipped remote integration tests; ARM64 debug APK built and signature verified.

### Final verification

- [x] Record exact baseline/final commands and outcomes.
- [x] Obtain one independent whole-patch review and resolve material findings.
- [x] Confirm original documents and launch/runtime/renderer/input backends are preserved.
- [x] Explicitly separate build verification from the physical-device Minecraft smoke flow.
- [ ] Run the Minecraft smoke flow on a configured Android device before declaring Bootstrap release-ready.
