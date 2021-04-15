package com.bcp.androidchallenge.data.local.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bcp.androidchallenge.data.local.database.dao.CurrencyDao
import com.bcp.androidchallenge.data.local.database.entity.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Currency::class), version = 2, exportSchema = false)
public abstract class CurrencyRoomDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var currencyDao = database.currencyDao()

                    currencyDao.deleteAll()

                    var currency = Currency("United States",0.8400,"USD","Dólares", "SOURCE")
                    currencyDao.insert(currency)
                    currency =Currency("Perú",0.2300,"SOL","Soles","DESTINATION")
                    currencyDao.insert(currency)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CurrencyRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CurrencyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}