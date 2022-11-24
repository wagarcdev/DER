package com.wagarcdev.der.data.local

import androidx.room.TypeConverter
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod

class DatabaseConverters {
    private val listSeparator = ", "

    @TypeConverter
    fun convertStringListToString(
        strings: List<String>
    ): String = strings.joinToString(listSeparator)

    @TypeConverter
    fun convertStringToStringList(
        string: String
    ): List<String> = string.split(listSeparator)

    @TypeConverter
    fun convertClimateToString(
        climate: Climate
    ): String = climate.name

    @TypeConverter
    fun convertStringToClimate(
        string: String
    ): Climate = when (string) {
        Climate.Sun.name -> Climate.Sun
        Climate.Rain.name -> Climate.Rain
        Climate.Snow.name -> Climate.Snow
        else -> Climate.Unspecified
    }

    @TypeConverter
    fun convertDayPeriodToString(
        dayPeriod: DayPeriod
    ): String = dayPeriod.name

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