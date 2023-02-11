package com.jmzd.ghazal.noteappmvp.ui.main

import androidx.appcompat.app.AlertDialog
import com.jmzd.ghazal.noteappmvp.base.BasePresenterImpl
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.data.repository.main.MainRepository
import com.jmzd.ghazal.noteappmvp.utils.ALL
import com.jmzd.ghazal.noteappmvp.utils.HIGH
import com.jmzd.ghazal.noteappmvp.utils.LOW
import com.jmzd.ghazal.noteappmvp.utils.NORMAL
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter  @Inject constructor(
    private val repository: MainRepository,
    private val view: MainContracts.View , ) :
    MainContracts.Presenter, BasePresenterImpl() {


    override fun loadAllNotes() {
        disposable = repository.loadAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //it: List<NoteEntity>
                if (it.isNotEmpty()) {
                    view.showAllNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }

    override fun deleteNote(entity: NoteEntity) {
        disposable = repository.deleteNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.deleteMessage()
            }
    }

    override fun filterNote(priority: String) {
        disposable  = repository.filterNote(priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //it: List<NoteEntity>
                if (it.isNotEmpty()) {
                    view.showAllNotes(it)
                } else {
                    view.showEmpty()
                }
            }
    }
}