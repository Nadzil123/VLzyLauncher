# Service configuration review — 2026-10-01

The independent reviewer approved the scoped change with no Critical, Important,
or Minor findings. Review covered the working tree on `main`, including the
missing-configuration guards, error mapping, Modrinth defaults, build input
normalization, regression tests, and setup documentation. It excluded previously
reviewed Bootstrap and download changes.

The reviewer confirmed that the official CurseForge API guard runs before
transport without blocking public CDN/unrelated hosts; Microsoft checks run before
device-code, polling, and refresh requests; both account and content errors reach
the setup messages; saved platform choices remain intact; and documentation does
not claim that the missing integrations are activated.

The implementer supplied fresh evidence of 101 passing tests, three optional
remote skips, all five new regression tests passing, a successful ARM64 debug
build, and APK signature verification. The reviewer inspected test source without
rerunning the build. See [service configuration](../service-configuration.md) for
the artifact identity and hash.

## Scope rulings

- **Configured credentials and full Xbox/Minecraft authentication:** accept the
  review exclusion; these need owner-provided registrations and device checks.
  They remain explicitly unverified and are not included in the fix claim.
- **Optional mirror availability/error selection:** accept the review exclusion
  because source routing is unchanged. A mirror-specific or Modrinth-specific
  device error requires its actual source and error text; the verified diagnosis
  here is the absent official-service configuration.
- **Full device readiness, earlier fixes, and historical signing changes:** accept
  the scoped exclusion. Prior fixes have separate review records; the physical
  Minecraft smoke gate remains open in the Bootstrap ledger.

The first review session was interrupted before a verdict could be recovered;
this final review completed after the build evidence was recovered. Changes are
local and uncommitted; no remote push or publication occurred.
