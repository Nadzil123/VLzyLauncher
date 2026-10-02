# Branding completion

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

Remaining upstream names are intentional:

- Author, fork, library, and license acknowledgements describe their actual sources.
- Internal packages, JNI bindings, Gradle module paths, renderer-plugin metadata,
  file-manager storage, and shared version metadata retain compatibility identifiers.
- Upstream dependency/contributor links refer to those projects. The fork project
  and release actions already open `Nadzil123/VLzyLauncher`.

In particular, the shared `versions/<version>/ZalithLauncher/` directory remains in
place so per-version settings and icons survive. Upstream copyright notices and
the preserved upstream README sections are not renamed to claim VLzy authorship.

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

APK: `ZalithLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-bootstrap.4-arm64-v8a.apk`
(220,125,184 bytes). SHA-256:

```text
976ace063f0b197b10a2159724fd97651cc1320bda83efd45688282be85de962
```

The final command was `:ZalithLauncher:testDebugUnitTest :ZalithLauncher:assembleDebug
--continue -Darch=arm64`, using the external host tools described in
[Bootstrap verification](bootstrap-verification.md). An earlier attempt was
cancelled by a session restart; the verified final build ran independently of the
chat session and recorded exit code 0 in its completion file.

The owner's successful gameplay report applies to `bootstrap.3`. A physical-device
check of the new label and a launch after upgrading to `bootstrap.4` remain useful;
the workspace does not provide an attached device session.
