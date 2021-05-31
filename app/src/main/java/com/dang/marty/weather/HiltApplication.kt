package com.dang.marty.weather

import dagger.hilt.android.HiltAndroidApp

/**
 * IN order tos upport android test with hilt. Any application level code should og  into Base Application
 */
@HiltAndroidApp
class HiltApplication: BaseApplication() {
}