# VLzyLauncher — Ecosystem Research Protocol

**Document type:** RESEARCH PROCESS\
**Status:** Living\
**Purpose:** Guidance for Codex/project agents when researching Android Minecraft Java launcher ideas.

---

# 1. Principle

> **Study broadly. Choose deliberately. Build coherently.**

VLzy is ecosystem-informed, not ecosystem-assembled.

---

# 2. Research Scope

Search for:

- Android Minecraft Java launchers,
- active forks,
- renderer projects,
- driver/plugin systems,
- runtime build projects,
- input/control systems,
- mod/content managers,
- compatibility tooling.

---

# 3. Current Reference Snapshot — 2026-10-01

This list is **not exhaustive**.

## ZalithLauncher2
Reference:
https://github.com/ZalithLauncher/ZalithLauncher2

Useful for:
- modern Compose/Material direction,
- Zalith-specific runtime/renderer/plugin foundations,
- Pojav-derived launch machinery.

## FoldCraftLauncher (FCL)
Reference:
https://github.com/FCL-Team/FoldCraftLauncher

Useful for:
- Java 8/17/21/25,
- custom Java import,
- content management,
- renderer/driver/native plugin management,
- SDL3 integration,
- control conversion.

## Turtle Launcher
Reference:
https://github.com/Endiq-jar/TurtleLauncher

Useful for:
- Material 3 UX goals,
- fast-startup goals,
- low-RAM goals,
- performance-oriented product direction,
- integrated content management.

## HyperLauncher
Reference:
https://github.com/hollowlauncher/HyperLauncher

Useful for:
- instance management,
- custom Java,
- renderer selection,
- modpack import,
- performance tuning,
- modern Android launcher goals.

## MojoLauncher
Reference:
https://github.com/MojoLauncher/MojoLauncher

Useful as:
- important Pojav-derived lineage,
- compatibility/launcher architecture reference.

## Amethyst-Android
Reference:
https://github.com/AngelAuraMC/Amethyst-Android

Useful for:
- rendering technology exploration,
- broad Minecraft compatibility,
- stability/performance direction,
- mod installation experience.

## PojavLauncher
Reference:
https://github.com/PojavLauncherTeam/PojavLauncher

Useful as:
- historical foundation for much of the ecosystem.

## Other forks / experiments

Examples currently visible:
- SolCraftLauncher,
- CryonixLauncher,
- SDL-focused Zalith forks,
- independent Zalith/Mojo/Amethyst forks.

These are research inputs, not automatic VLzy dependencies.

---

# 4. The List Must Stay Open

Before major design decisions, search current public sources for:

```text
new Android Minecraft Java launchers
new forks
active repositories
new rendering technologies
new plugin architectures
new runtime approaches
new content-management approaches
new input approaches
```

A small project may still be worth studying if it introduces a meaningful idea.

---

# 5. What To Extract

For each relevant project, inspect:

- product goals,
- UX ideas,
- performance goals,
- runtime strategy,
- graphics strategy,
- instance strategy,
- content strategy,
- input strategy,
- recovery strategy,
- architectural lessons,
- limitations/failures worth avoiding.

Do not reduce research to a feature checklist.

---

# 6. Evidence Rules

When proposing a VLzy change based on another project:

- cite the source,
- note date/version where relevant,
- distinguish implemented functionality from roadmap goals,
- distinguish project claims from measured facts,
- inspect changelogs/issues/source when the decision is important.

README claims such as "low RAM" or "high performance" are project goals unless independently measured.

---

# 7. Candidate Evaluation

Evaluate every idea against:

```text
User value
VeryLazy alignment
Performance impact
Reliability impact
Compatibility value
Complexity
Maintainability
Security
Storage impact
Battery impact
Network impact
Architecture fit
```

---

# 8. Decision Categories

Every candidate becomes:

```text
CORE
ADAPT
LATER
REJECT
```

Never auto-adopt.

Correct flow:

```text
Discover
↓
Understand why it exists
↓
Compare to VLzy
↓
Evaluate
↓
Propose
↓
Concept update if accepted
↓
Spec update
↓
Implementation-plan update
↓
Implementation
```

---

# 9. Avoid Duplicate Concepts

Normalize terminology before comparing.

Examples:

```text
profile / instance / installation
renderer plugin / graphics plugin
Java manager / runtime manager
mod browser / content browser
```

Different names do not necessarily mean different ideas.

---

# 10. When Codex Should Research

Research current ecosystem when:

- beginning a major milestone,
- redesigning a major subsystem,
- changing graphics/runtime/content/input architecture,
- Minecraft changes materially,
- Android changes materially,
- a new relevant launcher gains meaningful activity.

Do not constantly poll without a reason.

VeryLazy applies to research too.

---

# 11. Research Report Template

```markdown
# Ecosystem Finding

Project:
Repository:
Date checked:
Activity/status:

## What it does

## Interesting goals

## Interesting UX/technical ideas

## Evidence

## Existing VLzy equivalent

## Potential VLzy value

## Costs / risks

## Decision
CORE / ADAPT / LATER / REJECT

## Proposed concept change
None / description
```

---

# 12. Research Does Not Override Concept

Hierarchy:

```text
Ecosystem Research
       ↓ proposal
Living Concept
       ↓
Living Specification
       ↓
Living Implementation Plan
       ↓
Code
```
