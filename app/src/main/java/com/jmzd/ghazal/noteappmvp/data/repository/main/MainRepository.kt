package com.jmzd.ghazal.noteappmvp.data.repository.main

import com.jmzd.ghazal.noteappmvp.data.database.NoteDao
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {
    fun loadAllNotes() = dao.getAllNotes()
    fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
}