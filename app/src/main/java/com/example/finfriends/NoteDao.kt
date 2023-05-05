package com.example.finfriends

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("select * from notes_Table order by id asc")
    fun getAllNotes() : LiveData<List<Note>>

    @Update
    fun update(note: Note)

}