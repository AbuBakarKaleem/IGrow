package com.app.igrow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.igrow.data.local.dao.CurrenciesDao
import com.app.igrow.data.local.models.CurrencyNamesEntity
import com.app.igrow.data.local.models.CurrencyRatesEntity
import com.app.igrow.utils.Constants.DATABASE_NAME

@Database(
    entities = [CurrencyRatesEntity::class, CurrencyNamesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}