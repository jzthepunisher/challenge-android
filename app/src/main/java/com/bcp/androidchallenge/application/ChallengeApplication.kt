package com.bcp.androidchallenge.application

import android.app.Application
import com.bcp.androidchallenge.data.local.database.room.CurrencyRoomDatabase
import com.bcp.androidchallenge.data.repository.CurrencyDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ChallengeApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CurrencyRoomDatabase.getDatabase(this, applicationScope) }
    //////val repository by lazy { CurrencyDataRepository(database.currencyDao()) }
}