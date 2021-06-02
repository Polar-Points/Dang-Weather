package com.dang.marty.weather.presentation.model

interface BaseSettingsModel {

    fun getType(): Type

    enum class Type {
        REGULAR;

        companion object {
            fun fromOrdinal(ordinal: Int): Type {
                return values().first { it.ordinal == ordinal }
            }
        }
    }
}

class SettingsModel(
    val title: String = "",
    val clickListener: (title: String) -> Unit): BaseSettingsModel {

    override fun getType(): BaseSettingsModel.Type = BaseSettingsModel.Type.REGULAR
}