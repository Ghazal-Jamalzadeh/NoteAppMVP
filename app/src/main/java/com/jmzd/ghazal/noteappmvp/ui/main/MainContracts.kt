package com.jmzd.ghazal.noteappmvp.ui.main

import com.jmzd.ghazal.noteappmvp.base.BasePresenter
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import dagger.Binds
import dagger.Provides

interface MainContracts {
    interface View{
        fun showAllNotes(notes: List<NoteEntity>)
        fun showEmpty()
        fun deleteMessage()
    }

    interface Presenter : BasePresenter {
        fun loadAllNotes()
        fun deleteNote(entity: NoteEntity)
    }
}