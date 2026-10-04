# VLzyLauncher

<img src="VLzyLauncher_Logo.png" alt="VLzyLauncher logo" width="160" height="160">

**VLzy Launcher** (VeryLazy Launcher) is an independent, unofficial Minecraft:
Java Edition launcher for Android, created and maintained by
[Nadzil123](https://github.com/Nadzil123). The interface defaults to English.

VLzyLauncher develops independently according to its own Concept and Specification.
Its code originates as a hard fork of ZalithLauncher2. Original authors, project
provenance and redistribution notices are preserved in
[UPSTREAM_NOTICE.md](UPSTREAM_NOTICE.md) and [LICENSE](LICENSE). VLzyLauncher is
not affiliated with Mojang or Microsoft.

## First 1.0.0 alpha

Release target: **VLzyLauncher v1.0.0-alpha.1-20261003**. Development now focuses
on 1.0.0; publication status and remaining checks are recorded in the
[handoff](VLzyLauncher_CODEX_HANDOFF.md).

- Create a local offline profile without Microsoft sign-in.
- Browse and download supported content through Modrinth.
- Install and launch Minecraft using the inherited runtime, renderer and input
  systems, with fixes for download completion and installer socket startup.
- Use the supplied VL logo, the `VLzy Launcher` app name and VLzy source/module
  identity throughout the project.

CurseForge is unavailable until further notice.
Microsoft sign-in is unavailable until further notice.

The living specification describes the direction of the project; it is not a
claim that every planned feature is implemented.

## Build

```sh
git clone https://github.com/Nadzil123/VLzyLauncher.git
cd VLzyLauncher
./gradlew :VLzyLauncher:testDebugUnitTest
./gradlew :VLzyLauncher:assembleDebug -Darch=arm64
```

The app module is [`VLzyLauncher/`](VLzyLauncher/), its source namespace is
`com.nadzil123.vlzylauncher`, and APKs are written to
`VLzyLauncher/build/outputs/apk/debug/`. See [build requirements and
signing](docs/bootstrap.md) before building. No release signing secrets belong
in this repository.

## Project documents

- [Concept](VLzyLauncher_Living_Concept.md)
- [Specification](VLzyLauncher_Living_Specification.md)
- [Implementation plan](VLzyLauncher_Living_Implementation_Plan.md)
- [Ecosystem research protocol](VLzyLauncher_Ecosystem_Research_Protocol.md)
- [Agent handoff](VLzyLauncher_CODEX_HANDOFF.md)
- [Complete naming and data migration](docs/project-naming.md)
- [Independent project and About](docs/independent-project.md)
- [Offline profiles](docs/offline-profiles.md)
- [Branding and supplied logo](docs/branding-followup.md)

Use [GitHub issues](https://github.com/Nadzil123/VLzyLauncher/issues) for reports,
[CONTRIBUTING.md](CONTRIBUTING.md) for code and translation contributions, and
[releases](https://github.com/Nadzil123/VLzyLauncher/releases) for published builds.
About links to these VLzyLauncher destinations and identifies Nadzil123 as the
project creator and maintainer. The app has no automatic upstream launcher
updater or sponsorship popup. Optional plugins still use their actual
third-party providers.
