package kr.co.local.room.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal object StringListTypeConverter {
    @TypeConverter
    @JvmStatic
    fun listToJson(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}