# Bootstrap verification

This records the initial Bootstrap build. For the newer `1.0.0-bootstrap.1`
download and language fixes, see [the follow-up verification](download-followup.md).

Dates: 2026-09-29–2026-09-30. Base:
`b6f0e68966821445607a8819e61ba981f2a55bbb`.

## Environment

Local verification runs on ARM64 Linux in Android PRoot. The system Java 21 is a
JRE without `javac`, so local checks use the installed JDK 25. Project bytecode
targets remain Java 17 and CI uses JDK 21.

Gradle 9.5.0, Kotlin 2.4.20, AGP 9.3.0, SDK 37.0/37.2 and NDK 25.2.9519653 are
unchanged from the repository. SDK/Gradle caches and host adapters are outside the
source tree under `/tmp/vlzy-bootstrap-*`. Matching x86_64 AAPT2 and NDK executable
tools run through QEMU locally; CI uses its standard x86_64 host tools.

## Checks

| Check | Result |
| --- | --- |
| Independent whole-patch review | One Important metadata-path finding fixed; see review record |
| Core and legacy-storage tests | 10 passed in both the pure JVM harness and the Android application test suite |
| Workflow syntax/expressions | All workflows passed actionlint 1.7.12; shellcheck/pyflakes integrations unavailable |
| Changed manifest and string XML | Parsed successfully |
| Original specification documents | Byte-identical to the supplied files; hashes below |
| Debug signing | Uses locally generated Android development keystore, without release credentials |
| Missing/partial release credentials | Both final `preReleaseBuild` checks rejected the inputs in `validateVlzyReleaseSigning`, listing only missing variable names |
| Signing report without release credentials | Debug key shown; release `Config: none`, with no provider error |
| Merged debug manifest | VLzy application ID, name, version and provider authorities confirmed |
| Full application tests | 94 total: **91 passed, 0 failed, 3 remote tests skipped** |
| ARM64 debug APK | `assembleDebug` passed, including Kotlin, Java, native compilation and packaging |
| APK metadata and signature | Correct VLzy ID/name/version and arm64-v8a ABI; `apksigner verify` passed |
| Physical Android/Minecraft smoke flow | Not run; no configured device session |

The core tests first failed to compile before the production types were added,
then all passed. The additional legacy-storage fixture test also passed with the
core suite. These JVM results do not substitute for application compilation or
device launch testing.

The full suite now uses temporary fixtures instead of two developer-specific
Windows file paths. Three inherited remote integration tests require an explicit
`VLZY_RUN_REMOTE_INTEGRATION_TESTS=true`; default runs report them as skipped.

The first full application execution had 94 tests: 85 passed, 6 failed, 3 skipped.
All six failures initially came from missing version catalog assets on the JVM
classpath. A Sync task now supplies only the two real catalog files through the
[AGP unit-test configuration callback](https://developer.android.com/reference/tools/gradle-api/9.3/com/android/build/api/dsl/UnitTestOptions#all).
With the catalog present, direct JUnit and Gradle runs both exposed two invalid
inherited expectations using a placeholder release and year-style version 25
(the parser supports year-style releases from 26). Test inputs now use real
catalog entries and assert snapshot-before-release ordering. Production
comparison code was not changed.

Final complete command (no tasks excluded) completed successfully in 13m 17s,
with 175 actionable tasks. Local execution used the environment/flags below and
`--daemon --continue` for this final run:

```sh
./gradlew :ZalithLauncher:testDebugUnitTest :ZalithLauncher:assembleDebug -Darch=arm64
```

Output: `ZalithLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-bootstrap-arm64-v8a.apk`
(216,330,121 bytes). APK SHA-256:

```text
223cd9790159eade54f26578117b1ccea881a8568c0313daf94450ad66c98025
```

Verified APK identity: `com.nadzil123.vlzylauncher.debug`, label `VLzyLauncher`,
version `1.0.0-bootstrap-debug` / `200043`, native ABI `arm64-v8a`.

## Build commands and baseline

On a supported x86_64 SDK host:

```sh
./gradlew :ZalithLauncher:testDebugUnitTest :ZalithLauncher:assembleDebug -Darch=arm64
./gradlew :ZalithLauncher:validateSigningDebug :ZalithLauncher:signingReport
./gradlew :ZalithLauncher:preReleaseBuild
```

The last command must fail if external release credentials are absent.

The untouched baseline first lacked an SDK/compiler. After provisioning, it reached
resource processing and failed to start the x86_64 AAPT2 daemon on this ARM64 host
(`/tmp/vlzy-bootstrap-gradle/daemon/9.5.0/daemon-1880.out.log`).

The final local application verification additionally uses:

```text
JAVA_HOME=/usr/lib/jvm/java-25-openjdk
ANDROID_HOME=/tmp/vlzy-bootstrap-sdk
GRADLE_USER_HOME=/tmp/vlzy-bootstrap-gradle
ANDROID_USER_HOME=/tmp/vlzy-bootstrap-android
-Pandroid.aapt2FromMavenOverride=/tmp/vlzy-bootstrap-host-tools/aapt2
-Dorg.gradle.jvmargs=-Xmx2g -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8
-Pkotlin.compiler.execution.strategy=in-process
--no-daemon --console=plain --max-workers=2
```

Inherited warnings include AGP's tested compile-SDK ceiling (37.1 versus 37.2),
non-positional formatting in existing translations, and duplicate LWJGL AAR
namespaces. Native compilation also reports existing printf/pointer warnings and
a missing optional `libawt_headless.so` cleanup target. These did not fail the build;
this slice does not suppress those warnings or change the native backend.

## Preserved documents and behavior

| File | SHA-256 |
| --- | --- |
| `VLzyLauncher_Specification_1.0.md` | `cb1f5f347d3dc75d47bae8d93ed6271a260c9047ee8e6747f7ac14058f768be8` |
| `VLzyLauncher_Repository_Gap_Analysis_Implementation_Plan_1.0.md` | `e4ca9c5d420b5a159b939daae30ed82583b9ce53687e954f72338c351857b5c1` |

Launch/runtime/graphics/input backends remain in place. The shared version metadata
directory remains `ZalithLauncher`; worlds, mods and configs are not moved.

Bootstrap must not be called release-ready before the device smoke flow in
[bootstrap.md](bootstrap.md) succeeds. Account integration, real Minecraft launch,
controls and safe exit remain separate from build and unit-test evidence.
