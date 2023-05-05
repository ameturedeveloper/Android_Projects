package com.example.finfriends

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "notes_Table")
class Note (

    @ColumnInfo(name = "title") val noteTitle : String,
    @ColumnInfo(name = "description") val noteDescription : String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "money") val noteMoney : String,

        ){

    @PrimaryKey(autoGenerate = true) var id = 0

}