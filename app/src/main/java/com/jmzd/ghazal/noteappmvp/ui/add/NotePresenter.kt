package com.jmzd.ghazal.noteappmvp.ui.add

import com.jmzd.ghazal.noteappmvp.base.BasePresenterImpl
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.data.repository.add.AddNoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotePresenter @Inject constructor(
    private val repository: AddNoteRepository ,
    private val view : NoteContracts.View ,
    ) : NoteContracts.Presenter , BasePresenterImpl() {


    override fun saveNote(entity: NoteEntity) {
        /*
        * این disposable را از کجا اوردیم ؟
        * چون از BasePresenterImpl ارث بری کردیم به تمام چیزهای پابلیکش دسترسی داریم
        * و با این کار هر زمان که فرگمنت ییا اکتیویتی من از چرخه حیاتش خارج شه اتوماتیک dispose میشه
        *
        * */
        disposable = repository.saveNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }



}