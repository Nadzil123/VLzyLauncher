package com.nadzil123.vlzylauncher.game.account

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class AccountSelectionTest {
    private val local = Account(
        uniqueUUID = "local-profile",
        username = "Player",
        profileId = "custom-saved-uuid",
        accountType = AccountType.LOCAL.tag
    )
    private val microsoft = Account(
        uniqueUUID = "microsoft-profile",
        username = "OnlinePlayer",
        profileId = "online-uuid",
        accountType = AccountType.MICROSOFT.tag
    )

    @Test
    fun `an offline profile is selectable without any Microsoft account`() {
        assertSame(local, selectAccountForLaunch(listOf(local), local.uniqueUUID))
    }

    @Test
    fun `restoring selection preserves the saved offline identity`() {
        val otherLocal = local.copy(uniqueUUID = "other-local", profileId = "other-uuid")
        assertSame(local, selectAccountForLaunch(listOf(microsoft, otherLocal, local), local.uniqueUUID))
    }

    @Test
    fun `missing selection prefers offline without replacing a saved online selection`() {
        val accounts = listOf(microsoft, local)
        assertSame(local, selectAccountForLaunch(accounts, "removed-profile"))
        assertSame(microsoft, selectAccountForLaunch(accounts, microsoft.uniqueUUID))
    }

    @Test
    fun `existing online-only profiles remain selectable`() {
        assertSame(microsoft, selectAccountForLaunch(listOf(microsoft), ""))
        assertNull(selectAccountForLaunch(emptyList(), ""))
    }

    @Test
    fun `local profiles need no online launch validation`() {
        assertTrue(local.isNoLoginRequired())
        assertFalse(AccountsManager.isLaunchCheckNeeded(local))
    }

    @Test
    fun `online profiles still need their own authentication`() {
        val external = microsoft.copy(
            uniqueUUID = "external-profile",
            accountType = "External server",
            otherBaseUrl = "https://auth.example.test"
        )
        assertFalse(microsoft.isNoLoginRequired())
        assertTrue(AccountsManager.isLaunchCheckNeeded(microsoft))
        assertFalse(external.isNoLoginRequired())
        assertTrue(AccountsManager.isLaunchCheckNeeded(external))
    }
}
