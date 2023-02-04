package com.jmzd.ghazal.noteappmvp.ui.main

import com.jmzd.ghazal.noteappmvp.base.BasePresenter
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity

interface MianContracts {
    interface View{
        fun showAllNotes(notes: List<NoteEntity>)
        fun showEmpty()
    }

    interface Presenter : BasePresenter {
        fun loadAllNotes()
    }
}