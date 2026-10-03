# Bootstrap independent review

Reviewed against base `b6f0e68966821445607a8819e61ba981f2a55bbb`.
Method: independent, read-only inspection of the whole Bootstrap patch.
Initial verdict: **with fixes**; one Important finding, no Critical or Minor findings.

## Important: branding changed shared version metadata paths — resolved

`launcher_name` supplies `BuildKeys.LAUNCHER_IDENTIFIER`. Before the fix, changing
that name made both `Version` path accessors select `VLzyLauncher/version.config`
instead of the existing `VLzyLauncher/version.config`. This would create default
settings for existing shared installations, affecting isolation, custom game paths,
renderer and JVM options. Export exclusions also used that display identifier.

The implementation now routes both path accessors through
`VersionStorage.directoryIn()` and uses its stable directory name for export
handling. No metadata migration occurs. `VersionStorageTest` verifies reading
an existing config/icon and writing back to the same config without creating a new
VLzy metadata directory. The ten core/storage tests passed together.

## Other inspected behavior

- UUID parsing rejects abbreviated identifiers and normalizes case for identity.
- Compatibility evidence is immutable; only ERROR findings block compatibility.
- A separate application ID preserves internal namespace/JNI names. Provider
  authorities match runtime package-name consumers.
- Home/About retain explicit fork attribution and upstream authorship.
- Existing update entry points no longer offer upstream APKs as fork updates.
- Debug and external release signing are separated; CI removes its temporary key.

Later local verification found that AGP's signing report could not inspect a
partially populated release signing config. The config is now created only when
all external fields are present; the release pre-build guard still rejects missing
fields. A subsequent signing report showed debug's local key and release `none`.

Review did not certify APK execution or device behavior. See
[verification](../bootstrap-verification.md) for build/test evidence and open gates.
