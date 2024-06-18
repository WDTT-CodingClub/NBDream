package kr.co.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.co.local.model.UserEntity
import kr.co.local.room.converter.StringListTypeConverter
import kr.co.local.room.dao.UserDao
import kr.co.nbdream.core.local.BuildConfig

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [StringListTypeConverter::class,]
)
internal abstract class NBDreamDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = BuildConfig.LIBRARY_PACKAGE_NAME + ".db"
        internal lateinit var instance: NBDreamDataBase

        @JvmStatic
        fun init(context: Context) = Room.databaseBuilder (
            context.applicationContext,
            NBDreamDataBase::class.java,
            DATABASE_NAME
        ).build()
    }
}