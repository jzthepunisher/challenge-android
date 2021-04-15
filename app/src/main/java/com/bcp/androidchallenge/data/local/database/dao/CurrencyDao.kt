package com.bcp.androidchallenge.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bcp.androidchallenge.data.local.database.entity.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("UPDATE currency_table SET exchange_rate = 'DESTINATION' WHERE sign = :sign AND exchange_rate = 'SOURCE'")
    suspend fun exchangeCurrencySource(sign :String?)

    @Query("UPDATE currency_table SET exchange_rate = 'SOURCE' WHERE sign = :sign AND exchange_rate = 'DESTINATION'")
    suspend fun exchangeCurrencyDestination(sign :String?)

    @Query("SELECT * FROM currency_table ORDER BY description ASC")
    suspend fun getCurrencies(): List<Currency>

    @Query("SELECT * FROM currency_table WHERE exchange_rate = :exchangeRate ORDER BY description ASC")
    suspend fun getCurrencySourceOrDestination(exchangeRate :String ): Currency

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: Currency)

    @Query("DELETE FROM currency_table")
    suspend fun deleteAll()

    @Query("UPDATE currency_table SET exchange_rate = '' WHERE exchange_rate = :exchangeRate ")
    suspend fun clearExchageRate(exchangeRate :String)

    @Query("UPDATE currency_table SET exchange_rate = :exchangeRate WHERE sign = :sign ")
    suspend fun updateExchageRate(sign :String, exchangeRate :String)

}