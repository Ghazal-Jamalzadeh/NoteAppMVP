package com.jmzd.ghazal.noteappmvp.data.database


import academy.nouri.s3_mvp.room.data.database.NoteDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}