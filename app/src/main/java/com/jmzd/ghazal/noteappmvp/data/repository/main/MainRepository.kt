package com.jmzd.ghazal.noteappmvp.data.repository.main

import com.jmzd.ghazal.noteappmvp.data.database.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {
    fun loadAllNotes() = dao.getAllNotes()
}