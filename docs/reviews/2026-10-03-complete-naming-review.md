# Complete naming review — 2026-10-03

An independent read-only reviewer inspected the staged complete-naming change
against `b8bd01e6313c225d45cb4fa1d83a240f571be736`.

## Result

No confirmed correctness blockers were found in the inspected changes. The
review covered JNI declarations and class lookups, shrinker rules, module and
workflow paths, renderer compatibility keys, and metadata migration. Existing
destination data is preserved; failed metadata moves fall back to the original.

## Non-blocking device caveat

The launcher activity now belongs to the VLzy namespace. Home-screen icons stored
by an Android launcher can refer to the previous component, so an in-place upgrade
may remove the icon or require the user to re-add it. The new app-drawer entry
remains available. This is documented in the release notes and requires a device
upgrade check. No legacy launch alias is introduced as part of the full rename.

## Verification limits

The reviewer did not independently verify the final alpha APK, signing, native
symbols or rebuilt LWJGL archives. Parent-side checks are recorded separately in
[project naming verification](../project-naming.md). Neither a device upgrade,
MMKV preference migration on Android, nor a Minecraft session on this alpha was
observed during this review.

The main audit found stale cursor packages in the bundled LWJGL JARs after the
source rename. Both JARs and extraction-version markers were subsequently rebuilt;
this correction is verified by source/archive and final-package checks rather
than attributed to the independent source review.
