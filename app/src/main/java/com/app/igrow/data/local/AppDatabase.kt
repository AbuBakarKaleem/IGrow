package com.app.igrow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.igrow.data.local.dao.*
import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.data.local.models.entities.ProductsEntityName
import com.app.igrow.utils.Constants.DATABASE_NAME

@Database(
    entities = [DiagnosticEntityName::class, DealersEntityName::class, ProductsEntityName::class, DistributorsEntityName::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diagnosticDao(): DiagnosticDao
    abstract fun productsDao(): ProductsDao
    abstract fun dealersDao(): DealersDao
    abstract fun distributorsDao(): DistributorsDao

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