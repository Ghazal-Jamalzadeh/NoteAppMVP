package com.jmzd.ghazal.noteappmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jmzd.ghazal.noteappmvp.R
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.data.repository.main.MainRepository
import com.jmzd.ghazal.noteappmvp.databinding.ActivityMainBinding
import com.jmzd.ghazal.noteappmvp.ui.add.NoteFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MianContracts.View {

    //Binding
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var presenter: MainPresenter

    //Other
    //private val presenter by lazy { MainPresenter(repository, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initViews
        binding.apply {
            //Load all notes
            presenter.loadAllNotes()
            //Note detail
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag) }
        }

    }

    override fun showAllNotes(notes: List<NoteEntity>) {
        binding.emptyLay.visibility = View.GONE
        binding.noteList.visibility = View.VISIBLE
    }

    override fun showEmpty() {
        binding.emptyLay.visibility = View.VISIBLE
        binding.noteList.visibility = View.GONE
    }
}