# VLzyLauncher — Living Concept

**Project:** VLzyLauncher\
**Meaning:** VeryLazy Launcher\
**Platform:** Android\
**Primary purpose:** Minecraft: Java Edition launcher for Android\
**Base:** ZalithLauncher2-derived foundation\
**Document type:** CONCEPT\
**Status:** Living document — intentionally changeable\

---

# 1. What This Document Is

This file defines **what VLzyLauncher is supposed to become**.

It is deliberately separate from:

- technical specification,
- implementation plan,
- code architecture,
- exact algorithms,
- file formats,
- class/module layout.

This document answers:

> What kind of launcher are we trying to build?

The concept may evolve as Android, Minecraft, renderers, and the launcher ecosystem evolve.

---

# 2. Core Identity

VLzyLauncher means:

> **VeryLazy Launcher**

Accepted naming — 2026-10-01 (**CORE**, requested by the project owner):

- Product/repository name: **VLzyLauncher**.
- Installed Android application name: **VLzy Launcher**.
- Use VLzy branding in launcher-owned messages and diagnostics while retaining
  clear upstream attribution and existing data compatibility.
- Use the owner-supplied green VL/rocket logo as the application artwork, retaining
  the original as `VLzyLauncher_Logo.png` (accepted 2026-10-02).

Scope correction accepted 2026-10-03 (**CORE**, requested by the project owner):
VLzy identity covers the repository's module folders, source packages, launcher
classes, native bridges, themes, build instructions and newly written metadata,
as well as the visible app. Preserve existing user data through migration. Keep
original authorship and genuine external compatibility references explicit;
they are not the fork's product identity.

Release direction accepted 2026-10-03 (**CORE**, requested by the project owner):
finish the complete naming change, then focus development on the 1.0.0 release.
The first alpha is published as **VLzyLauncher v1.0.0-alpha.1-20261003**. This is
an alpha milestone toward 1.0.0, not a claim that the full roadmap is complete.

Independent project ownership accepted 2026-10-04 (**CORE**, owner request):
VLzyLauncher is created and maintained by **Nadzil123** as an independent project.
Its code provenance is an independently maintained **hard fork of ZalithLauncher2**,
as clarified by the owner. It remains an **unofficial Minecraft: Java Edition
launcher**. The VLzy Concept and Specification govern its direction, features,
architecture and releases; changes do not require following the original project's
roadmap or synchronizing with it. Inherited code keeps its provenance and licenses.
About, support, contributions and release entry points must reflect that ownership.
Original-code authors belong in accurate acknowledgements and license notices;
they must not appear as the creator of VLzyLauncher. Retire unused original-project
update feeds and automatic upstream sponsorship prompts. Existing data migration
and optional third-party runtime/plugin compatibility remain supported.

Verification follow-up (**CORE**, 2026-10-04): installer communication must remain
reliable across immediate stop/start cycles, including complete release of the
previous listener's port before a new installer starts.

"VeryLazy" does not mean weak, incomplete, careless, or simplistic.

It means:

> Make the user do as little unnecessary work as possible, and make the launcher itself do as little unnecessary work as possible.

VLzy should be:

- modern,
- lightweight,
- powerful,
- automated where safe,
- transparent,
- highly compatible,
- modular,
- recoverable,
- Android-native in experience,
- suitable for normal users and power users.

Core identity:

> **Powerful underneath. Lazy on the surface.**

Alternative expression:

> **Less setup. More Minecraft.**

---

# 3. VeryLazy Has Two Meanings

## 3.1 VeryLazy for the user

The user should not need to manually solve technical problems that the launcher can safely solve.

Concept:

```text
Choose Minecraft / instance
↓
VLzy understands what is needed
↓
VLzy prepares what is missing
↓
Play
```

The launcher may help with:

- Java runtime selection,
- renderer selection,
- driver selection,
- loader setup,
- dependency setup,
- content compatibility,
- control profile,
- recovery after failure.

Advanced users still retain control.

---

## 3.2 VeryLazy for the launcher

VLzy itself should avoid unnecessary work.

> Do not initialize what is not needed.\
> Do not scan what has not changed.\
> Do not download what already exists.\
> Do not recompute what is still valid.\
> Do not keep resources alive without a reason.\
> Do not perform background work merely because it is possible.

When work is necessary:

> Do it efficiently, verify it, reuse the result, and avoid repeating it unnecessarily.

---

# 4. Living Concept, Not Frozen Concept

VLzyLauncher is a **living concept**.

It should be stable enough to maintain identity, but flexible enough to improve.

The concept may change when:

- a new launcher introduces a genuinely useful idea,
- Android platform behavior changes,
- Minecraft changes runtime/rendering requirements,
- a new renderer or compatibility technology becomes practical,
- testing shows an assumption was wrong,
- a design creates unnecessary complexity,
- real-world use shows a better direction.

Important:

> **Changeable does not mean directionless.**

New ideas must be evaluated against VLzy principles before adoption.

---

# 5. Ecosystem-Informed, VLzy-Filtered

VLzy should continuously study relevant Android Minecraft Java launchers, forks, experiments, rendering projects, and supporting tools.

Workflow:

```text
Observe ecosystem
↓
Collect goals / ideas / lessons
↓
Compare
↓
Remove duplicates
↓
Evaluate
↓
Filter through VLzy principles
↓
Adopt / Adapt / Defer / Reject
```

VLzy is:

> **Ecosystem-informed, not ecosystem-assembled.**

We do not copy every feature because another launcher has it.

We study broadly, choose deliberately, and integrate coherently.

---

# 6. Current Ecosystem Reference Set

This list is **not exhaustive and must never be treated as permanent**.

Important current references include:

- ZalithLauncher2
- FoldCraftLauncher (FCL)
- Turtle Launcher
- HyperLauncher
- MojoLauncher
- Amethyst-Android
- PojavLauncher / Boardwalk lineage
- SolCraftLauncher
- CryonixLauncher
- relevant Zalith / Mojo / Amethyst forks
- new Android Minecraft Java launcher projects discovered later

Codex/project agents should periodically search for:

- new Android Minecraft Java launchers,
- meaningful forks,
- renderer experiments,
- plugin architectures,
- input approaches,
- content-management approaches,
- performance ideas,
- compatibility improvements.

A newly discovered launcher becomes a **research candidate**, not an automatic VLzy feature source.

---

# 7. External Idea Classification

Each idea should end in one category:

## CORE
Strong fit with VLzy's identity.

## ADAPT
Useful, but should be redesigned to fit VLzy.

## LATER
Potentially useful, but not important enough now.

## REJECT
Conflicts with VLzy goals, hurts reliability/performance, adds too much complexity, or provides too little value.

Example:

```text
Instance management
→ CORE

Multi-Java runtime support
→ CORE

Modular renderers
→ CORE

Automatic compatibility guidance
→ CORE

Cloud sync
→ LATER

AI as required launcher infrastructure
→ REJECT

Always-running heavy background scanner
→ REJECT
```

---

# 8. Product Philosophy

## Automation without loss of control

If VLzy can safely determine something, it should help.

If the choice materially belongs to the user, the user decides.

Automatic decisions should be inspectable.

## Simplicity without removing power

VLzy should not become simple by deleting advanced capability.

Instead:

```text
Simple default experience
+
deep controls when requested
```

## Lightweight despite feature depth

Feature depth must not automatically mean:

- slow startup,
- large idle RAM use,
- constant background work,
- unnecessary scans,
- excessive network activity,
- bloated base installation.

## Reliability before novelty

Launching Minecraft reliably matters more than a long feature list.

## Recoverability

When something breaks, VLzy should help the user recover.

## Compatibility as a first-class concern

Compatibility includes:

- Minecraft versions,
- Java versions,
- loaders,
- mods,
- renderers,
- drivers,
- native libraries,
- Android versions,
- GPU families,
- input devices.

---

# 9. Modern Android Experience

VLzy should feel like a modern Android app, not a desktop launcher compressed onto a phone.

Goals:

- touch-friendly,
- responsive,
- clean,
- readable,
- fast,
- suitable for phones,
- suitable for tablets/foldables where practical,
- compatible with physical keyboard/mouse,
- compatible with controllers.

Modern does not mean animation-heavy.

Modern means clear hierarchy, low friction, predictable interaction, and efficient use of space.

---

# 10. Instance-Centric Experience

The user should think in terms of:

> "the Minecraft setup I want to play"

rather than:

> "a pile of technical folders and global settings."

An instance conceptually combines:

- Minecraft version,
- loader,
- mods,
- modpack state,
- Java choice,
- graphics choice,
- controls,
- worlds,
- configuration.

Different instances can have different needs.

---

# 11. Runtime Philosophy

VLzy should support multiple Java generations instead of one global Java version.

Current target generations:

- Java 8
- Java 17
- Java 21
- Java 25

Java 25 is important for modern Minecraft, but legacy compatibility remains important.

Concept:

> **Use the Java runtime appropriate for the instance.**

---

# 12. Graphics Philosophy

Graphics should be modular and capability-aware.

VLzy should remain open to technologies such as:

- MobileGlues,
- LTW,
- Zink / Mesa paths,
- VirGL,
- GL4ES / NG-GL4ES,
- system Vulkan/OpenGL paths,
- compatible driver plugins,
- future renderer projects.

No single renderer should be assumed best for every device.

Concept:

```text
Device + game + mods + graphics capabilities
↓
Choose / recommend a suitable path
```

Advanced users can override.

---

# 13. Modular Components

Large optional components should not automatically become permanent core baggage.

Examples:

- renderers,
- drivers,
- compatibility layers,
- runtimes,
- optional integrations.

Concept:

> **Available does not have to mean installed. Installed does not have to mean active.**

---

# 14. Content Philosophy

VLzy should make modded Minecraft easier without becoming a noisy marketplace.

Goals:

- discover content,
- install compatible content,
- understand dependencies,
- understand conflicts,
- import modpacks,
- maintain modpack state,
- reduce manual file movement.

Important content sources may include:

- Modrinth,
- CurseForge,
- local files,
- imported packs.

---

# 15. Input Philosophy

Android input is broader than touchscreen.

VLzy should treat these as first-class:

- touchscreen,
- virtual mouse,
- keyboard,
- physical mouse,
- controller/gamepad,
- hybrid combinations.

Users should not constantly switch one global "input mode."

---

# 16. Doctor / Recovery Philosophy

VLzy should aim to understand common failure categories such as:

- runtime problems,
- graphics problems,
- missing dependencies,
- content conflicts,
- missing files,
- storage problems,
- failed updates.

Concept:

> **Explain what is known, say when something is uncertain, and offer safe recovery when possible.**

AI is optional, not required.

---

# 17. Zero-Work Idle

VLzy should strive for near-zero unnecessary work while idle.

If nothing meaningful needs to happen:

> **VLzy should do almost nothing.**

Avoid:

- constant provider polling,
- repeated compatibility calculation,
- repeated mod scanning,
- unnecessary timers,
- unnecessary sensor use,
- unnecessary plugin initialization.

This is a central VeryLazy concept.

---

# 18. Game Handoff

When Minecraft becomes the main foreground workload, VLzy should avoid competing with it.

Concept:

```text
VLzy
↓
prepare game
↓
Minecraft starts
↓
VLzy reduces nonessential work/resources
↓
Minecraft receives priority
```

Retain only what is actually needed for coordination, safe state, user-visible required services, and recovery.

---

# 19. Offline-Capable Philosophy

If a local instance already has everything needed to launch, VLzy should avoid requiring internet without reason.

Accepted direction — 2026-10-01 (**CORE**, requested by the project owner):

- A user can create and select a local offline profile without first adding a Microsoft account.
- Reuse the existing local-profile and launch machinery; keep saved identities stable.
- Microsoft sign-in and CurseForge integration are deferred for now. Present their availability plainly in English, without a promised return date.
- An offline profile is a local identity. Online services retain their own authentication requirements.

Online access is naturally still needed for:

- account refresh where required,
- downloads,
- content discovery,
- update metadata.

---

# 20. Transparency

The user should be able to understand:

- selected Java,
- selected renderer,
- selected driver,
- compatibility warnings,
- update changes,
- proposed fixes.

Normal users should not be forced to read the details, but the details should exist.

---

# 21. What VLzy Should Feel Like

Normal user:

```text
Open
↓
Choose instance
↓
Play
```

Installing content:

```text
Search
↓
Install
↓
VLzy resolves what can be resolved safely
↓
Play
```

Power user:

```text
Open advanced details
↓
control runtime / graphics / JVM / input / content
```

Broken instance:

```text
VLzy gathers useful evidence
↓
explains likely problem
↓
offers safe recovery
```

---

# 22. What VLzy Must Not Become

VLzy should not become:

- a launcher overloaded with random features,
- an ad platform,
- a mandatory cloud service,
- AI-dependent,
- a launcher with constant background resource use,
- a launcher full of fake "FPS boost" switches,
- a launcher that silently changes user files,
- a launcher with hundreds of unexplained toggles on the main screen,
- a copy of another launcher,
- a product that sacrifices reliability for visual novelty.

---

# 23. Evolution Rule

When a new idea appears, ask:

1. Does it meaningfully improve Android Minecraft?
2. Does it align with VeryLazy?
3. Does it improve/preserve reliability?
4. Does it avoid unnecessary resource cost?
5. Does it preserve advanced user control?
6. Is it maintainable?
7. Does VLzy already solve this?
8. Can it be integrated coherently?

---

# 24. Concept Change Process

```text
New idea / ecosystem discovery
↓
Concept proposal
↓
Evidence / reason
↓
CORE / ADAPT / LATER / REJECT
↓
Concept update if accepted
↓
Spec update afterward
↓
Implementation-plan update afterward
```

Do not change implementation first and invent the concept afterward.

---

# 25. Document Hierarchy

```text
LIVING CONCEPT
      ↓
LIVING SPECIFICATION
      ↓
LIVING IMPLEMENTATION PLAN
      ↓
CODE
```

Separately:

```text
ECOSYSTEM RESEARCH
      ↓
feeds proposals into CONCEPT
```

---

# 26. Final Concept Statement

VLzyLauncher is a modern Android Minecraft Java launcher that studies the broader launcher ecosystem, selectively adopts useful lessons, and combines them into a coherent VeryLazy experience.

Its defining goal is:

> **High capability with low unnecessary effort — for both the user and the device.**

The concept is intentionally living.

It can improve.

It should not drift.
