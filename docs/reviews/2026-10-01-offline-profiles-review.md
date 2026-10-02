# Offline profiles — focused independent review

Reviewed the working-tree changes on `main` against the owner's 2026-10-01 request
and the latest living Concept, Specification, and Implementation Plan. Base commit:
`b6f0e68966821445607a8819e61ba981f2a55bbb`. This is a scoped follow-up to the earlier
bootstrap, download, and service reviews, not a repeat review of those patches.

The review covered the local account selector, removal of the inherited
regional/Microsoft prerequisite, account UI and Play routing, the two English
availability notices, and account regression tests. It was read-only and did not
run a competing build.

## Finding and resolution

One Important finding, no Critical or Minor findings:

- `AccountManageViewModel.fetchMicrosoftCapes` displayed the raw exception message.
  For a retained Microsoft account with an expired token, the profile request can
  receive HTTP 401 and request a refresh. In the unconfigured build, refresh throws
  `ServiceNotConfiguredException`; the old handler then exposed the earlier setup
  message instead of the required availability notice. The handler now calls
  `formatAccountError(th)`, matching the other account error paths. Re-login
  handling is unchanged. The parent checked the call chain and applied the
  correction; the reviewer independently confirmed it.

Final scoped verdict: **no remaining Critical, Important, or Minor findings**.
Explicit selections and saved UUIDs remain intact, missing selection prefers local,
online authentication remains distinct, and disabled Microsoft sign-in blocks
menu clicks. The final test/APK run and device/UI validation are separate checks.

## Verification ownership and limits

The parent ran the initial complete application suite: 110 tests, 107 passed,
0 failures/errors, and 3 optional remote tests skipped. The initial APK build also
passed. The reviewed message correction was applied after compilation, so the
parent reran the full suite and APK build. The final run passed with the same test
counts; signature and metadata verification also passed.
[Offline profiles](../offline-profiles.md) records the final result and artifact
identity.

Physical-device UI and Minecraft launch remain untested. The review did not
provision credentials, change account types, or broaden into unrelated runtime,
download, graphics, or security work.
