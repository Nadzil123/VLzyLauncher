# Contributing to VLzyLauncher

VLzyLauncher is an independent, unofficial Minecraft: Java Edition launcher,
created and maintained by
[Nadzil123](https://github.com/Nadzil123). Development, issue reports and pull
requests belong in [Nadzil123/VLzyLauncher](https://github.com/Nadzil123/VLzyLauncher).
The code began as a hard fork of ZalithLauncher2 and develops independently.
See the [changelog](CHANGELOG.md) and
[GitHub issues](https://github.com/Nadzil123/VLzyLauncher/issues) for published
changes and current work.

## Report a problem

Open a [GitHub issue](https://github.com/Nadzil123/VLzyLauncher/issues) with the
launcher version, Android version, device/architecture, Minecraft version and
mod loader, steps to reproduce, and the expected and actual result. Include a
relevant launcher or game log after removing account tokens and personal data.
For downloads, include the selected platform and the failing file or project.

Microsoft sign-in and CurseForge are unavailable until further notice. Use an
offline profile and supported Modrinth content in this alpha.

## Code and documentation

- Target `main` and keep changes focused on the reported problem.
- Discuss substantial changes in a GitHub issue before implementation.
- Follow the [build and signing guide](docs/BUILDING.md). Run relevant unit
  tests and compile the changed app before submitting.
- Use English for new interface text and project documentation. Preserve
  existing user data, offline profiles, and supported plugin compatibility.
- Keep original copyright notices and dependency licenses. See
  [UPSTREAM_NOTICE.md](UPSTREAM_NOTICE.md) and [LICENSE](LICENSE).

## Translations

Submit translation changes through this repository. English source strings live
in `VLzyLauncher/src/main/res/values/strings.xml`; locale translations live in
the corresponding `values-<locale>/strings.xml` directories. Keep resource keys,
format placeholders and XML escaping intact. The project has no separate
VLzyLauncher translation server configured.

## Builds and releases

Published builds belong on the
[VLzyLauncher releases page](https://github.com/Nadzil123/VLzyLauncher/releases).
The app opens that page for launcher updates. Native and renderer plugin links
can lead to third-party providers; those plugins are separate from VLzyLauncher
releases. Do not commit signing keys or service credentials.
