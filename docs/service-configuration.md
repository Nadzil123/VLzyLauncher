# Online service configuration

The owner deferred CurseForge and Microsoft sign-in on 2026-10-01. The current
direction is [offline profiles](offline-profiles.md), with English availability
notices and no Microsoft-account prerequisite. Credential setup is future work,
not a prerequisite for using local profiles.

The diagnosis and setup reference below describe the earlier service follow-up.
Enabling either integration in a future build requires VLzyLauncher's own service
configuration. Modrinth public browsing does not require an API token.

## Diagnosed failures (2026-10-01)

The `1.0.0-bootstrap.1-debug` build had neither integration configured. Presence-only
checks found no values in the build environment, ignored local files, or the Gradle
properties used by the local build. Requests using the same launcher User-Agent
reproduced these responses without using any account credentials:

| Request | Result |
| --- | --- |
| CurseForge mod search without `x-api-key` | HTTP 403 |
| Modrinth shader search without a token | HTTP 200 |
| Modrinth modpack search without a token | HTTP 200 |
| Microsoft device-code request with empty `client_id` | HTTP 400, `invalid_request`, code `900144` |

These results explain the known configuration failures; they do not diagnose every
403 or 400 from another service, an invalid nonempty key, a blocked network, or an
external authentication server.

## Future build configuration reference

When integration work resumes, configure one of the following sources.
Values are read when building the APK, so changing them requires a rebuild.

| Feature | Environment variable / GitHub Actions secret | Ignored file in repository root | Private Gradle property |
| --- | --- | --- | --- |
| Microsoft sign-in | `OAUTH_CLIENT_ID` | `.oauth_client_id.txt` | `oauth_client_id` |
| CurseForge | `CURSEFORGE_API_KEY` | `.curseforge_api.txt` | `curseforge_api_key` |

Use the ignored files or your private `GRADLE_USER_HOME/gradle.properties` for local
builds. Keep the CurseForge key out of tracked files and logs. Each ignored file
contains only its value. Nonblank environment values take precedence over the
local file, then the Gradle property; surrounding whitespace is removed.

For Microsoft, register an application that supports personal Microsoft accounts
and enable public client/device-code authentication. Supply its **Application
(client) ID**, not a password, access token, or client secret. The launcher uses
the `/consumers` device-code flow and then Xbox/Minecraft authentication. A nonempty
ID alone does not verify that the registration is valid or that the complete game
authentication flow succeeds.

For CurseForge, obtain a key for your third-party service through the application
process linked in the official API documentation. An arbitrary string or a GitHub
token will not work. The repository workflows already read the two secret names
shown above.

Primary references:

- [CurseForge API access and authentication](https://docs.curseforge.com/rest-api/)
- [Microsoft device-code flow and required client ID](https://learn.microsoft.com/en-us/entra/identity-platform/v2-oauth2-device-code)
- [Microsoft public client configuration](https://learn.microsoft.com/en-us/entra/identity-platform/scenario-desktop-app-configuration#enable-public-client-flow)
- [Modrinth API authentication and User-Agent requirements](https://docs.modrinth.com/api/)

## Service guard behavior and future verification

The service configuration follow-up makes Modrinth the default for mods, modpacks,
resource packs, and shaders when no platform preference is saved. Existing saved
choices are preserved; switch the platform filter to **Modrinth** if CurseForge was
selected earlier. Worlds remain CurseForge-only in the inherited implementation.

An unconfigured official CurseForge API request or Microsoft login/refresh reports
an availability message before making an invalid request. Public CurseForge CDN downloads
and unrelated hosts remain accessible. These guards do not activate the missing
integrations or replace remote validation of configured credentials.

Before distributing a build with the integrations enabled, verify on a device:

1. Search CurseForge, open a project, and download a permitted file.
2. Complete Microsoft device-code sign-in with an eligible account.
3. Verify game ownership/profile retrieval and refresh the saved session.
4. Search and download from Modrinth, including shaders and modpacks.

## Earlier verified artifact: bootstrap.2 (2026-10-01)

| Check | Result |
| --- | --- |
| Application tests | 104 total: **101 passed, 0 failures/errors, 3 optional remote tests skipped** |
| New configuration regression tests | All five passed; four failed as expected before the fix |
| ARM64 debug build | `BUILD SUCCESSFUL`, 21m 31s; 175 tasks, 36 executed |
| APK signature | `apksigner verify` passed |
| APK identity | `com.nadzil123.vlzylauncher.debug`, version code `200045`, version `1.0.0-bootstrap.2-debug` |
| Native ABI | `arm64-v8a` |
| Original specification and gap plan | Original SHA-256 values unchanged |
| Patch formatting | `git diff --check` passed |

Tests exercise the actual OkHttp/Ktor request boundaries and Microsoft login and
refresh entry points with network tripwires. The control test confirms that public
CDN and unrelated hosts still reach the transport when the key is absent. Tests
of missing configuration are conditional when a build actually has credentials;
all five ran without skips in this local build.

The full command was `:VLzyLauncher:testDebugUnitTest :VLzyLauncher:assembleDebug
--continue -Darch=arm64`, using the external ARM64 host-tool setup described in
[Bootstrap verification](bootstrap-verification.md). After a session interruption,
the completed build was recovered from `daemon-25792.out.log` (lines 915–916), the
fresh XML test reports, and the resulting APK. Signature, metadata, and hash were
then verified directly.

APK: `VLzyLauncher/build/outputs/apk/debug/VLzyLauncher-Debug-1.0.0-bootstrap.2-arm64-v8a.apk`
(216,333,437 bytes). SHA-256:

```text
f225e8801f30f857bc9d2ead44d8641c833aa727b7dab1469912b5e60299b14a
```

Changes remain local and uncommitted on `main`; no push or publication was made.
[Independent review](reviews/2026-10-01-service-configuration-review.md) found no
Critical, Important, or Minor issues in this scoped change.
No successful Microsoft sign-in or authenticated CurseForge request has been
established. Their registration/configuration and physical-device checks remain
required before any future re-enablement. Repository secret presence was not verified: the local GitHub CLI is
unavailable and the connected GitHub tools do not expose secret administration.
