package com.movtery.zalithlauncher.utils.network

import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.ui.AndroidStringText
import com.movtery.zalithlauncher.ui.androidText
import java.io.IOException

/** A missing build setting, rather than a remote server or account failure. */
class ServiceNotConfiguredException(val service: Service) :
    IOException("${service.displayName} is not configured in this build.") {
    enum class Service(val displayName: String) {
        CURSEFORGE("CurseForge"),
        MICROSOFT("Microsoft sign-in")
    }
}

fun ServiceNotConfiguredException.toLocal(): AndroidStringText = androidText(
    when (service) {
        ServiceNotConfiguredException.Service.CURSEFORGE -> R.string.error_curseforge_not_configured
        ServiceNotConfiguredException.Service.MICROSOFT -> R.string.error_microsoft_not_configured
    }
)
