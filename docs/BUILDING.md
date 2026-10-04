# Building VLzyLauncher

Ready-to-install APKs are available from
[GitHub Releases](https://github.com/Nadzil123/VLzyLauncher/releases).
These instructions are for contributors building the Android application.

## Requirements

- JDK 21 and the checked-in Gradle wrapper.
- Android SDK platforms 37.0 and 37.2, Build Tools 36.0.0 and NDK 25.2.9519653.
- An Android SDK location set through `ANDROID_HOME` or a local `local.properties`.
- An x86_64 Linux host for the standard Android SDK/NDK host tools. ARM64 hosts
  require compatible host tools or an x86_64 build environment.

```sh
git clone https://github.com/Nadzil123/VLzyLauncher.git
cd VLzyLauncher
./gradlew :VLzyLauncher:testDebugUnitTest -Darch=arm64
./gradlew :VLzyLauncher:assembleDebug -Darch=arm64
```

The application module is `VLzyLauncher/`; its source namespace is
`com.nadzil123.vlzylauncher`. Debug APKs are written to
`VLzyLauncher/build/outputs/apk/debug/` and use application ID
`com.nadzil123.vlzylauncher.debug`.

Three optional remote integration tests are skipped by default. Setting
`VLZY_RUN_REMOTE_INTEGRATION_TESTS=true` enables live server-ping and crash-log
upload tests; the upload tests publish their test content.

## Signing

Debug builds use a locally generated development key and need no release secrets.
A debug build from another machine may use a different certificate and cannot
necessarily update an existing installation. Keep signing material private.

Release builds use application ID `com.nadzil123.vlzylauncher` and require all
four signing values:

| Environment variable | External Gradle property |
| --- | --- |
| `VLZY_RELEASE_STORE_FILE` | `vlzyReleaseStoreFile` |
| `VLZY_RELEASE_STORE_PASSWORD` | `vlzyReleaseStorePassword` |
| `VLZY_RELEASE_KEY_ALIAS` | `vlzyReleaseKeyAlias` |
| `VLZY_RELEASE_KEY_PASSWORD` | `vlzyReleaseKeyPassword` |

Use an absolute path to a privately managed keystore. Supply credentials through
environment variables or a private `GRADLE_USER_HOME/gradle.properties`, never
through tracked project files or commands containing passwords.

```sh
./gradlew :VLzyLauncher:validateVlzyReleaseSigning
./gradlew :VLzyLauncher:assembleRelease -Darch=arm64
```

GitHub Actions release builds additionally require
`VLZY_RELEASE_KEYSTORE_BASE64` instead of a filesystem path. The workflow writes
the key to the runner's temporary directory and removes it after building.
Never reuse signing keys found in public source history.

## Publishing

Keep version history in `CHANGELOG.md` and tag the corresponding source commit.
Publish installable APKs and their SHA-256 checksums as GitHub Release assets.
Mark alpha builds as prereleases and state their supported architecture and
signing/build type. Do not upload signing keys, local plans or master artwork.

The release workflow builds signed APKs for stable releases. Prereleases use
explicitly uploaded, verified APKs and do not trigger the production signing job.
