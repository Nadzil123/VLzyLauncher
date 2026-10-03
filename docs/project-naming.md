# Complete project naming

The owner's 2026-10-03 correction extends VLzy branding to the entire project
layout and source identity. It supersedes the internal-name preservation described
in the earlier bootstrap and visual-branding reports.

| Area | Current identity |
| --- | --- |
| Product and repository | `VLzyLauncher` |
| Installed application | `VLzy Launcher` |
| Android module and directory | `VLzyLauncher` |
| Kotlin/Java package and Android namespace | `com.nadzil123.vlzylauncher` |
| Android application class | `VLzyApplication` |
| Native bridge classes | `VLzyBridge`, `VLzyBridgeStates`, `VLzyNativeInvoker` |
| Compose theme | `VLzyLauncherTheme` |
| Version metadata | `versions/<version>/VLzyLauncher/` |
| Selected version and favorites | `vlzy-game.cfg` |
| File-manager preference store | `vlzy_file_manager` |
| Build | `1.0.0-alpha.1-20261003`, code `200048` |
| GitHub release title | `VLzyLauncher v1.0.0-alpha.1-20261003` |

The application IDs, signing identity, account UUIDs, renderer identifiers, game
directories and controller socket identity are unchanged. Existing VLzy installs
can therefore receive an update without creating a separate application.

## Migration behavior

Version metadata and current-game state move on first access. Renaming preserves
the original bytes, including version settings, icons, logs and favorites. When
the new destination already exists, it takes precedence and the old data remains
recoverable. A failed move uses the original data instead of silently resetting it.
The process is idempotent. File-manager preferences copy typed values once and
never overwrite keys already present in the new store; the old store is retained
as a recovery source. Both old and new metadata directories remain excluded from
modpack exports by default.

## Deliberate provenance and compatibility references

An old project name is not an application identity in these locations:

- `vlzy/compatibility/LegacyNames.kt`: historical storage names and keys used by
  already-installed external renderer plugins. New installations write VLzy names.
- `path/UpstreamReferences.kt`: actual third-party project names and URLs. Replacing
  an external URL's owner/name would point to a different or nonexistent resource.
- Migration test fixtures: independent samples of the old on-disk format.
- [Upstream notices](../UPSTREAM_NOTICE.md), original copyright holders, and
  historical/research documentation: provenance must remain accurate.

Project-owned file paths, packages, native class references, classes, build tasks,
themes, current instructions and headers use the new names. The owner-supplied
`VLzyLauncher_Logo.png` remains byte-for-byte unchanged.

## Verification record

- Before implementation, the three updated storage tests failed because the old
  implementation still selected the legacy directory.
- After implementation, all eight standalone migration tests passed: directory
  moves, file moves, repeated access, conflicting destinations, failed moves,
  file/directory type conflicts, and selected-version/favorites preservation.
- Fresh native compilation and the full application build passed on 2026-10-03
  (`BUILD SUCCESSFUL`, 37m 54s; 175 tasks, 74 executed). Application results:
  117 total, 114 passed, 0 failures/errors, 3 optional remote tests skipped.
- The source/binary audit detected two bundled LWJGL JARs retaining old cursor
  class packages. Both versions and their extraction markers were rebuilt with
  Java 8 and Java 17 (`BUILD SUCCESSFUL`, 6m 7s; 8 tasks executed). The subsequent
  audit found no old tracked paths, source namespaces or bundled class namespaces.
- GitHub workflow validation passed with `actionlint`.
- Independent source review found no confirmed correctness blockers. It identified
  an upgrade caveat: an existing home-screen icon can retain the old activity
  component name. Some Android launchers may remove it or require the user to
  re-add the new app icon. See the [review record](reviews/2026-10-03-complete-naming-review.md).
- Final alpha application tests and ARM64/native build passed (`BUILD SUCCESSFUL`,
  43m 7s; 175 tasks, 174 executed). Results: 117 total, 114 passed, 0 failures/errors,
  3 optional remote tests skipped.
- The final APK has the expected alpha version, application ID and label. Its DEX
  files and native libraries contain no old application namespace. The audit
  confirmed 21 VLzy JNI symbols across four native libraries, and both packaged
  LWJGL JARs and extraction markers match the rebuilt source assets.
- APK signature verification passed, with the same certificate as `bootstrap.4`.
  The source logo is unchanged and the packaged logo is a 1254 × 1254 PNG.
- The final source/archive audit found no old tracked file paths, source namespaces
  or bundled class namespaces. Git's check of introduced whitespace also passed
  with file moves recognized as renames.

Final local artifact (ARM64 debug):

```text
VLzyLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-alpha.1-20261003-arm64-v8a.apk
Version: 1.0.0-alpha.1-20261003-debug (200048)
Application ID: com.nadzil123.vlzylauncher.debug
Size: 217334961 bytes
SHA-256: cfae3a5f6ff42d4d3ceb4c84a3414a9c319c7209da9d1e95ca1730393c55cf64
```

Physical-device follow-up must cover an in-place update, home-screen launch,
selected version/favorites, file-manager preferences and a Minecraft session.
The owner's earlier gameplay confirmation applies to `bootstrap.3`, not this
new namespace or alpha APK. No Android component alias is added solely to retain
the old product namespace; the new launcher entry is the supported entry point.

Build command:

```sh
./gradlew :VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug -Darch=arm64
```

When updating the shared LWJGL patch sources, regenerate bundled components before
assembling the app:

```sh
./gradlew :LWJGL:lwjgl-3.3.3:jar :LWJGL:lwjgl-3.4.1:jar
./gradlew :VLzyLauncher:assembleDebug -Darch=arm64
```

The LWJGL compiler toolchains are Java 8 and Java 17, as declared by their modules.
The Foojay resolver is updated to 1.0.0 because 0.8.0 references the removed
`JvmVendorSpec.IBM_SEMERU` field and cannot provision toolchains on Gradle 9.
The [official plugin release notes](https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention/1.0.0)
confirm that this version removes the obsolete reference.

GitHub publication remains pending write authentication; the earlier integration
upload returned `403: Resource not accessible by integration`.
