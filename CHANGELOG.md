# Changelog

User-facing changes are recorded here. Installable APKs are published on
[GitHub Releases](https://github.com/Nadzil123/VLzyLauncher/releases).

## [1.0.0-alpha.1-20261003] — 2026-10-04

### Added and changed

- Establish the **VLzy Launcher** app name, VL logo and VLzyLauncher source/module
  identity, including native bridges and bundled LWJGL classes.
- Identify Nadzil123 as creator and maintainer in About, with VLzy project,
  release, issue, contribution and license links.
- Credit ZalithLauncher2 as the original codebase of this independently
  maintained hard fork; preserve original authors and dependency licenses.
- Provide local offline profiles without a Microsoft sign-in prerequisite.
- Use Modrinth as the default supported content source.
- Default to English while preserving existing language preferences.
- Migrate existing launcher metadata, favorites and file-manager preferences
  without replacing newer data.
- Remove the unused original-launcher updater and automatic sponsorship popup.

### Fixed

- Correct download-completion handling and installer startup failures.
- Wait for active UDP reception to finish during installer socket shutdown,
  preventing intermittent port-binding failures when restarting the listener.

### Availability and upgrade notes

- The download is an **ARM64 debug APK**, intended for alpha testing. It retains
  the earlier VLzy development signing certificate and debug application ID.
- Microsoft sign-in and CurseForge are unavailable until further notice.
- This is an unofficial Minecraft: Java Edition launcher, not affiliated with
  Mojang or Microsoft.
- A renamed launcher activity may require re-adding the home-screen shortcut
  after updating; the app remains available in the app drawer.

[1.0.0-alpha.1-20261003]: https://github.com/Nadzil123/VLzyLauncher/releases/tag/v1.0.0-alpha.1-20261003
