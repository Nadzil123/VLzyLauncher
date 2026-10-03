# VLzyLauncher — Living Implementation Plan

**Document type:** IMPLEMENTATION PLAN\
**Status:** Living / repository-dependent\
**Derived from:** Living Concept + Living Specification\
**Repository:** https://github.com/Nadzil123/VLzyLauncher

---

# 1. Role

This file describes how the current repository can move toward the current Concept/Specification.

If it conflicts with the Concept:

> Concept wins.

If repository reality makes the Specification impractical:

> investigate, propose an update, then revise documents deliberately.

---

# 2. Migration Strategy

Use:

> **Preserve → Wrap → Replace → Remove old path**

Do not begin with a rewrite.

The existing ZalithLauncher2-derived code already contains mature machinery for:

- launch,
- runtimes,
- renderers,
- renderer/driver/native plugins,
- downloads,
- mod metadata,
- modpack import,
- input,
- Compose UI.

VLzy should preserve mature machinery and improve orchestration, automation, recovery, performance, and UX.

---

# 3. Research Before Major Changes

Before major subsystem redesign:

1. Read Living Concept.
2. Read Living Specification.
3. Read Ecosystem Research Protocol.
4. Search current ecosystem if the area may have evolved.
5. Compare new projects/ideas.
6. Do not auto-copy.
7. Propose concept/spec updates when warranted.

Especially for:

- graphics,
- runtime,
- input,
- content/modpacks,
- plugins,
- performance.

---

# 4. Phase 0 — Safe Baseline

Goals:

```text
VLzy branding
+
upstream compliance
+
clean build
+
working launch path
```

Tasks:

- user-facing branding,
- attribution,
- release-signing cleanup,
- baseline tests,
- ARM64 build,
- smoke test launch flow.

The initial namespace-preservation constraint was superseded by the owner's
2026-10-03 full naming correction; follow the coordinated rename plan below.

---

# 5. Phase 1 — VLzy Core Domain

Create logical package boundaries before large Gradle-module extraction.

Suggested:

```text
.../vlzy/
├── model/
├── compatibility/
├── transaction/
├── component/
├── repository/
└── migration/
```

Initial models:

```text
InstanceId
ComponentId
ComponentVersion
RuntimeRef
RendererRef
DriverRef
ContentRef

CompatibilityResult
CompatibilityReason

TransactionPlan
TransactionOperation
TransactionResult
```

Use adapters around the inherited launcher types.

---

# 6. Phase 2 — Instance Layer

Reuse/migrate existing `VersionConfig`.

Add stable VLzy instance identity.

Conceptually split:

```text
user intent
resolved state
operational / health state
```

Migration must:

- be idempotent,
- preserve existing game directories initially,
- preserve runtime/renderer/control choices,
- create recovery metadata.

---

# 7. Phase 3 — Runtime Layer

Wrap existing runtime manager.

Introduce:

```text
RuntimeRepository
RuntimeResolver
RuntimeValidator
RuntimeInstaller
RuntimeHealthChecker
```

Keep fallback/bundled runtimes until on-demand delivery is proven.

Add:

- metadata,
- architecture awareness,
- explainable resolution,
- verified installation,
- side-by-side upgrade where practical,
- Last Known Good.

---

# 8. Phase 4 — Graphics / Component Layer

Build on existing renderer/plugin system.

Do not create a competing graphics system.

Add abstractions for:

```text
renderer
driver
native component
capability
recommendation
health
```

Support optional technologies such as MobileGlues through the component/plugin path.

Add capability-aware recommendations, health, Last Known Good, and rollback.

---

# 9. Phase 5 — Unified Download / Transaction Layer

Wrap existing download engine.

Add:

```text
DownloadCoordinator
DownloadTask
DownloadGroup
ArtifactCache
```

Transaction flow:

```text
RESOLVE
PLAN
DOWNLOAD
VERIFY
STAGE
COMMIT
HEALTH CHECK
```

---

# 10. Phase 6 — Content Layer

Reuse existing mod metadata and modpack parsers.

Unify:

```text
project
version
file
dependency
origin
ownership
```

Implement/test:

- dependency graph,
- compatibility filtering,
- conflicts,
- ownership,
- modpack diffs.

---

# 11. Phase 7 — Input Layer

Preserve InputMap / LayerController / raw input.

Add normalization above it.

```text
Touch / Controller
        ↓
VLzy actions
        ↓
existing backend

Keyboard / Mouse
        ↓
raw path where appropriate
```

Add hybrid presentation switching without disabling other sources.

---

# 12. Phase 8 — VLzy UX Shell

Keep Compose.

Primary structure:

```text
Home
Library
Content
Settings
```

Migration:

```text
new VLzy shell
↓
embed existing functionality
↓
replace legacy screens gradually
```

---

# 13. Phase 9 — Doctor

Build deterministic diagnostics on current logs/crash evidence.

Initial rules:

- runtime mismatch,
- broken/missing runtime,
- graphics init failure,
- missing dependency,
- duplicate/conflicting content,
- missing/corrupt file,
- storage issue,
- interrupted transaction.

Prefer reversible fixes.

---

# 14. Phase 10 — VeryLazy Performance

This is cross-cutting.

Implement progressively:

## Lazy initialization
Only initialize subsystems when needed.

## Incremental scanning
Avoid scanning unchanged folders.

## Cache reuse
Reuse valid verified results.

## Bounded concurrency
Avoid excessive parallel work.

## Zero-Work Idle
Audit timers, polling, scanners, observers, sensors.

## Game Handoff
When Minecraft starts:
- trim nonessential caches,
- pause optional indexing,
- suspend unnecessary observers,
- avoid optional heavy work,
- retain minimum required launcher state.

## Regression measurement
Track:
- startup,
- RAM,
- large instance scan,
- mod-list scan,
- game preparation latency,
- UI jank,
- base install size.

Do not claim optimization without measurement.

---

# 15. Phase 11 — Security Hardening

Audit inherited permissions/network behavior before removal.

Goals:

- reduce broad storage access where practical,
- HTTPS by default,
- narrow exceptions,
- verify component downloads,
- secure archives,
- redact secrets,
- isolate release signing.

Preserve user data during migration.

---

# 16. CI / Quality Gates

Extend CI with:

- unit tests,
- migration tests,
- runtime resolver tests,
- dependency resolver tests,
- transaction tests,
- archive-security tests,
- component verification tests,
- Doctor tests.

Later add instrumentation, startup benchmarks, large-instance benchmarks, and device smoke testing.

---

# 17. Milestones

```text
M0  Safe baseline
M1  Core domain
M2  Instance migration
M3  Runtime + graphics orchestration
M4  Download + transactions
M5  Content resolver
M6  VLzy UX shell
M7  Doctor / recovery
M8  VeryLazy optimization
M9  Security / hardening
RC  Migration + rollback + device tests
1.0
```

Milestones can change as the Living Concept evolves.

---

# 18. New Ecosystem Idea Procedure

If Codex discovers a new launcher/idea:

Do not implement immediately.

Create a finding:

```text
Source project
What is new
Why it matters
Current VLzy equivalent
Benefits
Costs
Risks
Suggested decision
```

Classify:

```text
CORE
ADAPT
LATER
REJECT
```

If accepted:
1. update Concept,
2. update Specification,
3. update Implementation Plan,
4. implement.

---

# 19. Avoid Premature Work

Avoid:

- package renaming without coordinated native/build updates and data migration,
- huge early Gradle split,
- moving user data without migration,
- deleting runtime fallbacks too early,
- rewriting renderer system that works,
- forcing raw input through one abstraction,
- adopting every feature found elsewhere,
- background services without measured need.

---

# 20. First Coding Slice

The baseline branding, independent signing, and initial core models are implemented
and have build/unit-test evidence in `docs/bootstrap-progress.md`. On 2026-10-01 the
owner reported that the earlier errors were resolved and that they played Minecraft
with the `bootstrap.3` build. This is user-reported device gameplay evidence; the
full device checklist has not been independently observed.

Recommended first slice:

```text
1. Verify baseline build/tests
2. Apply VLzy branding
3. Confirm upstream attribution
4. Clean signing setup
5. Add VLzy core package
6. Add InstanceId model
7. Add compatibility model skeleton
8. Add tests
9. Verify existing Minecraft launch still works
```

## Accepted follow-up — Offline profiles (2026-10-01)

This bounded change reuses the inherited account backend; it is not an account-system rewrite.

1. Preserve the existing local profile editor, storage, UUID generation, and launch arguments.
2. Remove the regional Microsoft-account prerequisite from selected-account state and UI routing.
3. Resolve saved account selection consistently; prefer a local profile only when the saved selection is missing.
4. Put Offline first in the login menu and route Play-without-an-account to the local editor.
5. Replace the two unavailable integration messages with the exact English specification text; show Microsoft availability before a user attempts sign-in.
6. Verify selection and local/online authentication separation with meaningful unit tests, then run the application suite and ARM64 build.
7. Record artifact verification and the remaining device smoke check in `docs/offline-profiles.md`.

Local verification completed on 2026-10-01: 107 application tests passed, 3 optional
remote tests skipped, and the ARM64 `1.0.0-bootstrap.3` APK built and passed signature
verification. Independent review has no remaining findings after correcting cape
error formatting. The artifact and remaining physical-device smoke check are
recorded in `docs/offline-profiles.md`; changes remain local on `main`.

## Accepted follow-up — Branding completion (2026-10-01)

1. Set the installed display name to `VLzy Launcher` while retaining the stable
   `VLzyLauncher` product identifier.
2. Move account client-token, controller socket, and game-launch brand consumers
   from the display-name key to the existing stable identifier key.
3. Replace remaining launcher-owned Zalith log/crash wording and Vulkan probe name.
4. Preserve upstream acknowledgements. Internal namespace/path preservation was
   the initial approach; it is superseded by the 2026-10-03 correction below.
5. Install the owner-supplied logo unchanged, with Android resource wrappers for
   adaptive and fallback icons, and use it in splash/About. Remove obsolete icon
   assets after their replacements exist.
6. Verify the built label, identity compatibility, existing suite, ARM64 APK and
   signature; record the owner's gameplay report and review evidence before GitHub
   publication preparation.

Verified on 2026-10-02: 107 application tests passed, 3 optional remote tests
skipped, and the ARM64 `bootstrap.4` APK built with the exact `VLzy Launcher` label.
Signature and packaged-logo checks passed; old launcher artwork is absent.
Independent naming and asset reviews have no findings. Artifact details and the
remaining device-rendering check are in `docs/branding-followup.md`.

## Accepted follow-up — Complete project naming (2026-10-03)

The owner rejected limiting branding to visible UI. Execute on `main`:

1. Rename the app module to `VLzyLauncher` and source namespace to
   `com.nadzil123.vlzylauncher`, including native JNI, reflection, manifests,
   shrinker rules, application/bridge classes, themes and Gradle/workflow paths.
2. Use VLzy names in project-owned headers and English repository documentation,
   retaining copyright holders and centralizing original-project attribution.
3. Add regression tests for old/new metadata, destination conflicts, move failure,
   repeated access and current-game selection. Then implement migration to the
   new version directory, game-state filename and file-manager preference ID.
4. Isolate real third-party URLs and legacy plugin/environment keys. Accept the
   VLzy renderer flag while preserving existing plugin interoperability.
5. Build `:VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug -Darch=arm64`;
   inspect APK identity, native symbols, embedded class paths, signature and logo.
6. Audit tracked paths/content for unexplained old names, review the full change,
   and record evidence in `docs/project-naming.md`. GitHub authentication was
   restored on 2026-10-03; use authenticated Git to push `main` and verify the
   remote commit instead of repeating the earlier rejected integration upload.

Locally verified on 2026-10-03 in the first-alpha build: 114 tests passed, 3 optional
remote tests skipped, both LWJGL variants rebuilt, and the ARM64 APK passed
manifest, DEX/JNI, runtime-assets, logo and signature checks. Independent source
review found no confirmed blockers. See `docs/project-naming.md` for the artifact
and physical-device upgrade checks still outstanding.

## Accepted release direction — First 1.0.0 alpha (2026-10-03)

1. Finish the complete naming change, including rebuilt LWJGL assets, before
   claiming the rename is complete.
2. Set the base version to `1.0.0-alpha.1-20261003`, retaining code `200048`.
   This replaces the unpublished intermediate `bootstrap.5` build.
3. Verify the final APK and document the results and remaining device checks.
4. Commit on `main`; use tag `v1.0.0-alpha.1-20261003` and GitHub release title
   `VLzyLauncher v1.0.0-alpha.1-20261003` when publication access is available.
5. Update `VLzyLauncher_CODEX_HANDOFF.md` with the actual completion/publication
   state. Subsequent development focuses on 1.0.0; later roadmap work remains
   subject to the living concept/specification and evidence.

---

# 21. Implementation Philosophy

> Keep the mature machinery.\
> Improve orchestration.\
> Improve decisions.\
> Improve recovery.\
> Improve performance.\
> Improve experience.

And:

> Measure before optimizing.\
> Preserve before replacing.
