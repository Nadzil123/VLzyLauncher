# VLzyLauncher — Codex Handoff

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
