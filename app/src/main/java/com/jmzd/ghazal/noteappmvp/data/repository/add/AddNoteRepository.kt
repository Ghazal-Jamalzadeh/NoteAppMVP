package com.jmzd.ghazal.noteappmvp.data.repository.add

import com.jmzd.ghazal.noteappmvp.data.database.NoteDao
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import javax.inject.Inject

class AddNoteRepository @Inject constructor(private val dao: NoteDao) {
    fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
}