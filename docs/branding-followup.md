# Branding completion

Historical record for `bootstrap.4`. The owner subsequently clarified that
internal names and folders must also change. The current behavior is documented
in [complete project naming](project-naming.md). Commands and source paths below
have been updated to the current module layout; the test results remain evidence
for the earlier build only.

The owner reported on 2026-10-01 that the earlier errors were resolved and that
they played Minecraft to test `bootstrap.3`. They then requested remaining
launcher branding to use **VLzyLauncher**, with **VLzy Launcher** as the installed
application name. The living Concept, Specification, and Implementation Plan were
updated in that order before code changes.

## Naming and compatibility

| Surface | Name |
| --- | --- |
| Android app label, splash, About title, recent-app title | `VLzy Launcher` |
| Product/repository, APK prefix, game brand, launcher logs | `VLzyLauncher` |
| Short name | `VLzy` |
| Expanded name | `VeryLazy Launcher` |

The display label previously also supplied authentication client tokens and the
controller socket name. Those consumers now use the existing stable
`LAUNCHER_IDENTIFIER`. Its value remains `VLzyLauncher`, exactly the previous
display-name value, so existing token derivation and socket identifiers stay
consistent. Game-launch branding/arguments and generated export headers use the
same stable identifier. No account or data migration is needed for this rename.

Hard-coded SDL/gamepad log prefixes, the Vulkan capability-probe application name,
and one Portuguese crash message now identify VLzyLauncher.

This build originally retained internal package names, JNI bindings, Gradle module
paths and saved-data filenames. The owner rejected that limited scope on
2026-10-03. The follow-up renames them together and migrates existing metadata;
original authorship and external protocol compatibility remain explicit.

## Supplied artwork (2026-10-02)

The supplied PNG is preserved unchanged as `VLzyLauncher_Logo.png` and copied to
the density-independent Android resource `drawable-nodpi/vlzy_launcher_logo.png`.
Both files are 1,198,927 bytes with SHA-256:

```text
810af6cfff0b7b6ed406d65e3f18c008b8745919461e367f05e448ee2c9918d3
```

Adaptive icons use a black background and an inset wrapper around this artwork;
the fallback icons, splash, and About page use the same image. The old raster,
foreground, and monochrome launcher icons have been removed. A custom monochrome
version is not supplied, so older launchers that need one use the color icon when
themed icons are enabled, rather than displaying the upstream mark. Android's
[adaptive icon guidance](https://developer.android.com/develop/ui/compose/system/icon_design_adaptive)
describes that fallback. The original supplied artwork is not regenerated or
retouched. The three README introductions display the new logo as well.

## Verification

The final application suite and ARM64 build completed on 2026-10-02:

| Check | Result |
| --- | --- |
| Application tests | 110 total: **107 passed, 0 failures/errors, 3 optional remote tests skipped** |
| Build | `BUILD SUCCESSFUL`, 12m 37s; 175 tasks, 24 executed |
| Installed label | `VLzy Launcher` |
| APK identity | `com.nadzil123.vlzylauncher.debug`, code `200047`, version `1.0.0-bootstrap.4-debug` |
| Native ABI / signature | `arm64-v8a`; `apksigner verify` passed |
| Packaged artwork | New 1254×1254 PNG present; old launcher artwork absent |
| Supplied artwork | Master and source resource match the original bytes |
| Independent reviews | No findings in naming or logo integration |
| Patch formatting | `git diff --check` passed |

No artificial unit tests were added for text substitutions; checks use the actual
built Android label, signature, packaged resources, existing regression suite, and
stable-identity consumers. See the [review record](reviews/2026-10-02-branding-review.md).

APK: `VLzyLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-bootstrap.4-arm64-v8a.apk`
(220,125,184 bytes). SHA-256:

```text
976ace063f0b197b10a2159724fd97651cc1320bda83efd45688282be85de962
```

The final command was `:VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug
--continue -Darch=arm64`, using the external host tools described in
[Bootstrap verification](bootstrap-verification.md). An earlier attempt was
cancelled by a session restart; the verified final build ran independently of the
chat session and recorded exit code 0 in its completion file.

The owner's successful gameplay report applies to `bootstrap.3`. A physical-device
check of the new label and a launch after upgrading to `bootstrap.4` remain useful;
the workspace does not provide an attached device session.
