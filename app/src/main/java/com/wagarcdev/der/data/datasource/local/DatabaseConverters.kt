package com.wagarcdev.der.data.datasource.local

import androidx.room.TypeConverter
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod

/**
 * Room database converters.
 */
class DatabaseConverters {
    private val listSeparator = ", "

    /**
     * Convert an [List] of [String] into [String].
     */
    @TypeConverter
    fun convertStringListToString(
        strings: List<String>
    ): String = strings.joinToString(listSeparator)

    /**
     * Convert an [String] into [List] of [String].
     */
    @TypeConverter
    fun convertStringToStringList(
        string: String
    ): List<String> = string.split(listSeparator)

    /**
     * Convert an [Climate] into [String].
     */
    @TypeConverter
    fun convertClimateToString(
        climate: Climate
    ): String = climate.name

    /**
     * Convert an [String] into [Climate].
     */
    @TypeConverter
    fun convertStringToClimate(
        string: String
    ): Climate = when (string) {
        Climate.Sun.name -> Climate.Sun
        Climate.Rain.name -> Climate.Rain
        Climate.Snow.name -> Climate.Snow
        else -> Climate.Unspecified
    }

    /**
     * Convert an [DayPeriod] into [String].
     */
    @TypeConverter
    fun convertDayPeriodToString(
        dayPeriod: DayPeriod
    ): String = dayPeriod.name

    /**
     * Convert an [String] into [DayPeriod].
     */
    @TypeConverter
    fun convertStringToDayPeriod(
        string: String
    ): DayPeriod = when (string) {
        DayPeriod.Dawn.name -> DayPeriod.Dawn
        DayPeriod.Morning.name -> DayPeriod.Morning
        DayPeriod.Evening.name -> DayPeriod.Evening
        DayPeriod.Night.name -> DayPeriod.Night
        else -> DayPeriod.Unspecified
    }
}