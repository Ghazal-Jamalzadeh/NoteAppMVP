package com.jmzd.ghazal.noteappmvp.ui.main

import com.jmzd.ghazal.noteappmvp.base.BasePresenterImpl
import com.jmzd.ghazal.noteappmvp.data.repository.main.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter  @Inject constructor(
    private val repository: MainRepository,
    private val view: MianContracts.View) :
    MianContracts.Presenter, BasePresenterImpl() {


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
}