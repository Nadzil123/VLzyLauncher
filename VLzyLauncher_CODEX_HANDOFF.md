# VLzyLauncher — Codex Handoff

## Current release direction — 2026-10-03

The complete project rename is implemented and locally verified. Development now
focuses on **1.0.0**.
The owner specified the following identity for GitHub publication:

| Field | Value |
| --- | --- |
| GitHub release title | `VLzyLauncher v1.0.0-alpha.1-20261003` |
| Git tag | `v1.0.0-alpha.1-20261003` |
| Base Android version | `1.0.0-alpha.1-20261003` |
| Android version code | `200048` |
| Application display name | `VLzy Launcher` |
| Branch | `main` |

Debug builds retain the `-debug` version suffix and local development signing.
This alpha replaces the unpublished intermediate `bootstrap.5`; it does not
declare the full 1.0.0 roadmap complete. Keep application text and current project
documentation in English.

Current scope includes the `VLzyLauncher/` module, the
`com.nadzil123.vlzylauncher` namespace, application/native bridge names, bundled
LWJGL classes, and migration of existing launcher metadata. Preserve the supplied
`VLzyLauncher_Logo.png`, existing account identities, game data, and attribution.
See [complete naming and verification](docs/project-naming.md) for the evidence
and deliberate compatibility/provenance exceptions.

Local validation completed on 2026-10-03: 114 application tests passed, 3 optional
remote tests skipped, both LWJGL JARs rebuilt, and the ARM64 debug APK built and
passed manifest, DEX/JNI, runtime-asset, logo and signature checks. The certificate
matches the earlier VLzy debug build. Independent source review found no confirmed
correctness blockers. Artifact filename and SHA-256 are in the verification link
above; the alpha has not yet been tested on a physical device.

Publication is pending: the previous GitHub integration write returned
`403: Resource not accessible by integration`, and command-line Git lacked usable
credentials. Do not describe local commits or APKs as pushed or released. Retry
publication only after authentication or integration permissions change.
The latest `gh auth status` check also reports that no GitHub host is logged in.
Use the prepared [alpha release notes](docs/releases/v1.0.0-alpha.1-20261003.md)
when the tag and release can be published.

After this slice, prioritize alpha device validation and reliability toward 1.0.0.
Microsoft sign-in and CurseForge remain unavailable until further notice; offline
profiles and Modrinth remain the current supported paths. Do not silently expand
this release into a rewrite of all later roadmap phases.

## Reading order

Read in this order:

1. `VLzyLauncher_Living_Concept.md`
2. `VLzyLauncher_Ecosystem_Research_Protocol.md`
3. `VLzyLauncher_Living_Specification.md`
4. `VLzyLauncher_Living_Implementation_Plan.md`

---

# Document Separation

## Concept
Defines what VLzy should become.

## Ecosystem Research
Defines how to find and evaluate current/new launcher ideas.

## Specification
Converts accepted Concept goals into requirements.

## Implementation Plan
Explains how the current repo can reach the current Specification.

---

# These Documents Are Living

Do **not** permanently freeze them.

Evolution flow:

```text
Research / evidence
↓
Concept proposal
↓
Concept update
↓
Spec update
↓
Implementation-plan update
↓
Code
```

Do not casually reverse the order.

---

# Research Rule

Before major subsystem work, especially graphics/runtime/content/input/plugin/performance, search for current relevant Android Minecraft Java launcher development.

Current examples:

- ZalithLauncher2
- FoldCraftLauncher
- Turtle Launcher
- HyperLauncher
- MojoLauncher
- Amethyst
- Pojav lineage
- other active forks discovered later

A new launcher is a research candidate, not an automatic feature source.

---

# VLzy Filter

Evaluate ideas using:

```text
Does it reduce user friction?
Does it reduce unnecessary work?
Does it preserve advanced control?
Does it preserve/improve reliability?
Does it improve compatibility?
Is it maintainable?
Is it secure?
Does it create unnecessary background/resource cost?
Does VLzy already solve the same problem?
```

Classify:

```text
CORE
ADAPT
LATER
REJECT
```

---

# VeryLazy Reminder

For the user:
> Do not force the user to do work the launcher can safely do.

For the device:
> Do not make the launcher do work that does not need to happen.

Key rules:

```text
Do not initialize what isn't needed.
Do not scan what hasn't changed.
Do not download what already exists.
Do not recompute what is still valid.
Do not keep resources alive without a reason.
Do not work while idle without a reason.
Do not compete with Minecraft when Minecraft needs the device.
```

Repository strategy:

> **Preserve → Wrap → Replace → Remove old path**
