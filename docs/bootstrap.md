# VLzyLauncher Bootstrap

VLzyLauncher (VeryLazy Launcher) is an unofficial modified version based on
[ZalithLauncher2](https://github.com/ZalithLauncher/ZalithLauncher2).
Upstream copyright, GPL notices and bundled third-party licenses remain applicable.
VLzy is not affiliated with Mojang, Microsoft or the upstream project.

This first implementation slice adds fork branding, visible attribution, separate
development/release signing and pure core models. The existing launch, runtime,
renderer, input and content implementations remain the backend. The full 1.0
specification is a roadmap; Bootstrap is not the completed 1.0 release.

## Application identity

- Display name: **VLzy Launcher**; product/repository: **VLzyLauncher**; expanded name: **VeryLazy Launcher**.
- Release application ID: `com.nadzil123.vlzylauncher`.
- Debug application ID: `com.nadzil123.vlzylauncher.debug`.
- Internal namespace: `com.nadzil123.vlzylauncher` (including JNI symbols).
- Initial version: `1.0.0-bootstrap`, version code `200043`.
- Download follow-up: `1.0.0-bootstrap.1`, version code `200044`; development uses `main`.
- Service configuration follow-up: `1.0.0-bootstrap.2`, version code `200045`; Modrinth is the default content platform when no preference is saved.
- Offline profile follow-up: `1.0.0-bootstrap.3`, version code `200046`; local profiles work without a Microsoft-account prerequisite. See [offline profiles and verification](offline-profiles.md).
- Branding follow-up: `1.0.0-bootstrap.4`, version code `200047`; spaced Android label with stable existing account/socket/game identifiers. See [branding verification](branding-followup.md).
- Complete naming / first alpha: `1.0.0-alpha.1-20261003`, version code `200048`; module,
  source namespace, native bridges and metadata use VLzy names. See
  [migration behavior and verification](project-naming.md).
- Default interface language: English when no language preference is saved. Existing choices and the language picker are preserved.

VLzy installs alongside upstream. It does not move existing worlds, mods or game
directories. Existing shared version metadata migrates to
`versions/<version>/VLzyLauncher/` on first access, preserving settings, icons and
logs. Current-version/favorites state and file-manager preferences also adopt VLzy
names with recovery of old data. Private accounts/settings belonging to a separate
upstream app are not automatically imported. Home and About
identify the fork and preserve upstream authorship. The **VLzy releases** action
opens this repository's release page. Automatic upstream APK update checks are
disabled until a VLzy update feed is implemented.

## Build and tests

Use JDK 21, the checked-in Gradle wrapper, Android SDK platforms 37.0 and 37.2,
Build Tools 36.0.0 (the AGP default) and NDK 25.2.9519653. Point `ANDROID_HOME` or `local.properties`
at your SDK. Android's standard Linux SDK/NDK host binaries require x86_64; ARM64
hosts need compatible host tools or a supported x86_64 build environment.

```sh
./gradlew :VLzyLauncher:testDebugUnitTest
./gradlew :VLzyLauncher:assembleDebug -Darch=arm64
```

The regular test suite uses temporary file fixtures. Three inherited remote
integration tests (public crash-log uploads and Minecraft server ping) report
skipped unless `VLZY_RUN_REMOTE_INTEGRATION_TESTS=true` is explicitly set. CI runs
without that opt-in; these tests depend on live services and the upload tests
publish their test content.

JVM tests receive the application's two version catalog assets through a generated
test classpath directory. Snapshot ordering tests use valid entries from that
catalog; the production comparator remains unchanged.

The APK is written beneath `VLzyLauncher/build/outputs/apk/debug/`.
Microsoft sign-in and CurseForge are deferred until further notice; local profiles
do not require either integration. The current build shows English availability
notices, disables unconfigured Microsoft sign-in, and defaults content browsing
to Modrinth when no preference was saved. [Online service configuration](service-configuration.md)
retains the earlier 400/403 diagnosis and setup reference for future integration work.

## Signing

Debug uses Android's locally generated development key. No release credentials are
needed for debug builds or unit tests. Release builds require all four values:

| Environment variable | External Gradle property |
| --- | --- |
| `VLZY_RELEASE_STORE_FILE` | `vlzyReleaseStoreFile` |
| `VLZY_RELEASE_STORE_PASSWORD` | `vlzyReleaseStorePassword` |
| `VLZY_RELEASE_KEY_ALIAS` | `vlzyReleaseKeyAlias` |
| `VLZY_RELEASE_KEY_PASSWORD` | `vlzyReleaseKeyPassword` |

Use an absolute keystore path and environment variables or your private
`GRADLE_USER_HOME/gradle.properties`. Environment variables take precedence.
Do not put passwords in commands, project properties, Git or build logs.

```sh
./gradlew :VLzyLauncher:validateVlzyReleaseSigning
./gradlew :VLzyLauncher:assembleRelease -Darch=arm64
```

Release tasks fail when signing is incomplete or the keystore is unreadable. CI
additionally needs `VLZY_RELEASE_KEYSTORE_BASE64`, containing the external keystore
encoded as base64, instead of a filesystem path. CI writes it to the runner's
temporary directory only for a Release build and removes it after the build.
The Debug workflow does not receive release signing secrets.

Inherited keystores/passwords have been removed from the current tree, but their
historical commits remain. Treat those keys as exposed and use a fresh privately
generated key for VLzy releases. No new release key is generated or registered by
this change.

## Model foundation

`InstanceId` wraps a UUID, normalizes canonical UUID strings and rejects abbreviated
or malformed strings. Names and directories are not identity. It is not yet written
to existing `VersionConfig` data; migration is a separate, recoverable operation.

`CompatibilityResult` captures reasons from a completed evaluation. INFO/WARNING
are nonblocking; ERROR is incompatible. Rule codes and explanations are required.
An empty result means evaluated rules found no problems, not that unknown
components are implicitly supported. Runtime/graphics rules will consume these
types in later phases.

## Device smoke gate

On 2026-10-01 the owner reported that `bootstrap.3` resolved the earlier errors and
that they successfully played Minecraft. This is user-reported gameplay evidence;
it does not independently verify every scenario below or the newer branding build.

Before calling this slice release-ready, verify on an ARM64 Android device:
startup and visible attribution → account path → installed version recognition →
game preparation → Minecraft launch → controls → safe return after exit.
Build and unit-test success alone do not satisfy this gate.
