package com.leoleo.androidgithubsearch.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone Landscape",
    device = Devices.PHONE,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
)
annotation class PreviewPhoneDevice

@Preview(
    showBackground = true,
    device = Devices.TABLET,
    name = "Tablet"
)
annotation class PreviewTabletDevice

@Preview(
    showBackground = true,
    device = Devices.FOLDABLE,
    name = "Foldable"
)
annotation class PreviewFoldableDevice

@Preview(
    showBackground = true,
    device = Devices.DESKTOP,
    name = "Desktop"
)
annotation class PreviewDesktopDevice

@PreviewPhoneDevice
@PreviewTabletDevice
@PreviewFoldableDevice
@PreviewDesktopDevice
annotation class PreviewDevices