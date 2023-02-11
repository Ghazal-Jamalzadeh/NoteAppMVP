package com.jmzd.ghazal.noteappmvp.ui.add

import com.jmzd.ghazal.noteappmvp.base.BasePresenter
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity

interface NoteContracts {

    interface View {
        /* این close مربوط به بعد از زدن دکمه سیو است. دکمه بستن معمولی نیاز به مدیریت شدن از طرف پرزنتر ندارد
        * زمانی نیاز داریم که به واسه اطلاعاتی که از پرزنتز میاد قراره تغییری در ویوی ما اتفاق بیفته */
        fun close()
        fun loadNote(note : NoteEntity)
    }

    interface Presenter : BasePresenter {
        fun saveNote(entity: NoteEntity)
        fun detailNote(id : Int )
        fun updateNote(note : NoteEntity)
    }
}