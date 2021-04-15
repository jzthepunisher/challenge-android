package com.bcp.androidchallenge.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class Currency (
    @ColumnInfo(name = "description") val description :String,
    @ColumnInfo(name = "euro_equivalence") val euroEquivalence :Double,
    @PrimaryKey @ColumnInfo(name = "sign") val sign :String,
    @ColumnInfo(name = "sign_description") val signDescription :String,
    @ColumnInfo(name = "exchange_rate") val exchangeRate :String
) {
}