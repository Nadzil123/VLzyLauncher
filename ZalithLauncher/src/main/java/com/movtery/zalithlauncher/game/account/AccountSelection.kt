package com.movtery.zalithlauncher.game.account

/** Preserve a saved selection; prefer a local profile when that selection is missing. */
internal fun selectAccountForLaunch(accounts: List<Account>, selectedId: String): Account? =
    accounts.find { it.uniqueUUID == selectedId }
        ?: accounts.firstOrNull { it.isLocalAccount() }
        ?: accounts.firstOrNull()
