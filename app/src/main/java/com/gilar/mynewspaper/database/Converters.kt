package com.gilar.mynewspaper.database

import androidx.room.TypeConverter
import com.gilar.mynewspaper.model.Source

class Converters {

    @TypeConverter
    fun fromSource(s: Source):String = s.name

    @TypeConverter
    fun toSource(name:String): Source = Source(name,name)
}