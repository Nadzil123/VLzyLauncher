# VLzyLauncher — Living Specification

**Document type:** SPECIFICATION\
**Status:** Living / versioned / changeable\
**Derived from:** `VLzyLauncher_Living_Concept.md`\
**Important:** This document is NOT the concept itself.

---

# 1. Role

The Concept defines what VLzy wants to become.

This Specification translates accepted concept goals into requirements.

It changes when:

- the Living Concept changes,
- repository constraints are discovered,
- Android/Minecraft requirements change,
- research reveals a better technical requirement.

The Specification should not independently invent product direction.

---

# 2. Core Requirements

VLzy shall:

- preserve a reliable Minecraft Java launch path,
- support instance-specific configuration,
- support multiple Java runtimes,
- support modular graphics,
- support modern content workflows,
- support Android-first input,
- provide diagnostics/recovery,
- minimize unnecessary overhead,
- preserve advanced user control.

Accepted branding requirements — 2026-10-01:

- Android's app label, splash title, About app title, and recent-app title shall use
  **VLzy Launcher**; the home product header uses **VLzyLauncher**.
- The product identifier, generated APK prefix, and launcher diagnostic brand shall
  use **VLzyLauncher**.
- A display-name change shall not change stored account identity, authentication
  client tokens, the controller socket identifier, or existing game/settings paths.
- Keep upstream author/license acknowledgements and compatibility identifiers;
  launcher-owned error messages and logs must identify the VLzy fork.
- Preserve the supplied logo bytes as `VLzyLauncher_Logo.png` and use that artwork
  for launcher icons, splash, and About. Replace the old themed-icon reference so
  supported launcher configurations cannot select the upstream artwork.

Full naming scope — owner correction accepted 2026-10-03:

- The Android module and directory shall be `VLzyLauncher`; source packages and
  JNI class/symbol references shall use `com.nadzil123.vlzylauncher`.
- Rename launcher application/bridge/theme classes, native guards, Gradle tasks,
  workflow paths and current documentation together. Original copyright holders
  and license text remain accurate.
- Keep application IDs and signing unchanged so this remains an update to the
  existing VLzy app. Preserve stored account UUIDs and renderer/control choices.
- New version metadata uses `VLzyLauncher/`, selected-version state uses
  `vlzy-game.cfg`, and file-manager preferences use `vlzy_file_manager`.
- Migrate existing metadata without overwriting newer destination data; repeated
  access is idempotent. A failed move must preserve and use the existing data.
- Isolate old storage/plugin identifiers and real upstream service URLs in
  explicitly documented compatibility/provenance files. Do not fabricate renamed
  external URLs or silently break installed renderer plugins.
- Fresh native compilation, manifest/JNI checks, migration regressions, application
  tests and an ARM64 APK are required after this namespace change.

Release identity — owner instruction accepted 2026-10-03:

- GitHub release title: `VLzyLauncher v1.0.0-alpha.1-20261003`.
- Git tag: `v1.0.0-alpha.1-20261003`; base Android version name:
  `1.0.0-alpha.1-20261003`. Debug builds retain their `-debug` suffix.
- Keep the Android label `VLzy Launcher` and monotonic version code `200048`.
- Record verified work, remaining release checks and the 1.0.0 focus in
  `VLzyLauncher_CODEX_HANDOFF.md`. Work and publication use `main`.

Independent project identity — owner instruction accepted 2026-10-04:

- Retain the explicit `unofficial Minecraft: Java Edition launcher` description.
  VLzyLauncher is an independent project and an independently maintained hard
  fork of ZalithLauncher2. Use `hard fork` for code provenance rather than `port`.
- VLzy's own Concept and Specification govern development. Project identity,
  releases and contribution routing are independent; no upstream synchronization
  workflow is required. Preserve accurate original-code credits and licenses.
- About shall identify `Nadzil123` as the creator and maintainer of `VLzyLauncher`.
- Verification found an existing UDP restart race: socket closure can precede
  completion of a blocked receive operation. Installer shutdown shall wait for
  that receive operation to unwind before reusing the port, preserve exclusive
  binding, and remain safe when called from a receive callback.
- Project, releases, issue reporting, contribution instructions and license links
  shall use `Nadzil123/VLzyLauncher`; the creator profile shall use `Nadzil123`.
- Original-project authors and translators remain clearly labelled acknowledgements,
  with their actual provenance and licenses. Do not relabel their work as original
  VLzy code or send VLzy support/contribution actions to their project.
- Remove the unused upstream launcher-update implementation and its metadata/mirror
  endpoints. Launcher updates remain the user-invoked VLzy releases page.
- Remove the inherited automatic sponsor popup and its launcher-level support URL.
  Do not invent a donation account for the owner.
- Keep existing version/account/game data, legacy migration keys and external-plugin
  protocols compatible. Optional third-party plugin sources must be identified as
  external sources, not a VLzy-owned distribution service.
- New ownership text is English, consistent with the established language default.

---

# 3. Instance Requirements

Each instance shall have stable identity.

VLzy should separate conceptually:

```text
user intent
resolved component state
operational / health state
```

Instance capabilities should include:

- rename without identity loss,
- per-instance Java,
- per-instance graphics,
- per-instance controls,
- content isolation,
- safe migration from inherited launcher configuration,
- recovery metadata,
- snapshot/rollback capability where practical.

---

# 4. Runtime Requirements

Initial Java generations:

```text
8
17
21
25
```

Runtime selection should consider:

- user pin,
- Minecraft requirement,
- loader/modpack constraints,
- architecture,
- available runtimes.

VLzy shall not globally force Java 25 on legacy instances.

Runtime management should support:

- detection,
- import,
- on-demand installation,
- validation,
- health state,
- version/build identity,
- rollback where practical.

---

# 5. Graphics Requirements

Graphics architecture shall distinguish:

```text
renderer
driver
native compatibility component
```

Automatic graphics recommendations may consider:

- GPU,
- Android capabilities,
- Minecraft,
- loader,
- mods,
- shaders,
- compatibility information,
- local successful history.

Automatic decisions must be explainable.

External graphics components should remain optional where practical.

---

# 6. Component Requirements

Managed components should expose enough metadata to identify:

```text
ID
version
type
architecture
source
hash
size
compatibility
install status
health
```

Support:

```text
available != installed != active
```

---

# 7. Content Requirements

Relevant sources include:

- Modrinth,
- CurseForge,
- local imports,
- modpacks.

Content resolution should understand:

- Minecraft compatibility,
- loader compatibility,
- required dependencies,
- optional dependencies,
- conflicts,
- version constraints.

Track source/ownership where practical.

---

# 8. Input Requirements

Support:

- touchscreen,
- virtual mouse,
- physical keyboard,
- physical mouse,
- gamepad/controller,
- hybrid input.

Do not break raw physical hotkeys by forcing all input through high-level mappings.

---

# 9. Doctor Requirements

Doctor should initially be deterministic.

Initial diagnostic areas:

- wrong/incompatible Java,
- missing/broken runtime,
- graphics initialization failure,
- missing dependency,
- content conflict,
- duplicate content,
- missing/corrupt file,
- storage issue,
- interrupted operation.

Findings should include:

```text
evidence
severity
explanation
recommended action
risk
reversibility where known
```

---

# 10. Performance Requirements

VLzy shall treat performance as measurable.

Directions:

- fast startup,
- lazy initialization,
- lazy metadata loading,
- incremental scanning,
- caching,
- bounded parallelism,
- avoid repeated fingerprints,
- avoid repeated compatibility computation,
- avoid initializing unused components,
- heavy work off UI thread.

---

# 11. Zero-Work Idle

When no meaningful task is pending, minimize:

- wakeups,
- provider polling,
- rescans,
- compatibility recomputation,
- plugin initialization,
- sensor activity,
- background allocations.

Idle is a performance state.

---

# 12. Game Handoff

When Minecraft is active, VLzy should minimize resource competition.

Where safe:

- release unnecessary UI/image caches,
- pause nonessential discovery/indexing,
- avoid optional compatibility refresh,
- avoid nonessential heavy work,
- retain only required coordination/recovery state.

---

# 13. Cache Validity

Caches need a clear validity basis.

Relevant inputs may include:

```text
Minecraft
loader/version
content fingerprint
runtime
renderer
driver
device capabilities
rule/version metadata
```

Prefer targeted invalidation over clearing/recomputing everything.

---

# 14. Download Requirements

Provide where practical:

- progress,
- retry,
- cancellation,
- verification,
- bounded parallelism,
- reuse of verified artifacts,
- persistent long-operation state.

---

# 15. Transaction Requirements

High-impact mutation should favor:

```text
resolve
plan
download
verify
stage
commit
health check
```

Failure before commit should preserve prior active state where practical.

---

# 16. Security Requirements

Goals include:

- HTTPS by default,
- narrow cleartext exceptions,
- safe archive extraction,
- artifact verification,
- no release signing secrets in source control,
- log secret redaction,
- cautious migration away from broad storage access.

Security migration must preserve user data.

---

# 17. Offline Requirements

When local artifacts are complete, local management and launch should work without unrelated online services.

Network-dependent actions should fail gracefully.

Accepted offline-profile requirements — 2026-10-01:

- Allow offline profile creation and selection without a Microsoft account or a regional prerequisite.
- Offer Offline first in the account menu; when Play needs an account, open offline profile creation.
- Preserve a saved selected profile and its UUID. When that selection no longer exists, prefer an available offline profile.
- Local profiles must not enter Microsoft login, token refresh, or online session validation during launch preparation.
- Keep existing Microsoft and external-server accounts as their original types; their authentication behavior remains distinct from offline profiles.
- While unavailable, show exactly: "CurseForge is unavailable until further notice." and "Microsoft sign-in is unavailable until further notice."
- An unavailable Microsoft sign-in option must not invite a failing sign-in attempt.

---

# 18. Compatibility States

Avoid fake percentages.

Use meaningful states such as:

```text
Recommended
Compatible
Experimental
Known Issues
Unsupported
Unknown
```

---

# 19. Ecosystem Research Requirement

Before major subsystem redesigns, consult:

`VLzyLauncher_Ecosystem_Research_Protocol.md`

External ideas must first be accepted conceptually before becoming requirements.

---

# 20. Testing Requirements

High-risk areas require tests:

- runtime resolver,
- dependency resolver,
- compatibility rules,
- migration,
- transactions,
- archive extraction,
- component verification,
- Doctor rules.

Performance must be benchmarked rather than guessed.

---

# 21. Change Rule

This Specification is living.

Accepted Concept changes may require Spec changes.

Do not permanently freeze early technical assumptions.

Do not destabilize working behavior merely to chase a newer design.
