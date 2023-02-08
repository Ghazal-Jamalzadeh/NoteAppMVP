package com.jmzd.ghazal.noteappmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.data.repository.main.MainRepository
import com.jmzd.ghazal.noteappmvp.databinding.ActivityMainBinding
import com.jmzd.ghazal.noteappmvp.ui.add.NoteFragment
import com.jmzd.ghazal.noteappmvp.utils.BUNDLE_ID
import com.jmzd.ghazal.noteappmvp.utils.DELETE
import com.jmzd.ghazal.noteappmvp.utils.EDIT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MainContracts.View {

    //Binding
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var presenter: MainPresenter

    //Other
//    private val presenter by lazy { MainPresenter(repository, this) }

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
            //Clicks
            noteAdapter.setOnListItemClickListener { entity, state ->
                when (state) {
                    EDIT -> {
                        val noteFragment = NoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID, entity.id)
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager, NoteFragment().tag)
                    }
                    DELETE -> {
                        val noteEntity = NoteEntity(entity.id, entity.title, entity.desc, entity.category, entity.priority)
                        presenter.deleteNote(noteEntity)
                    }
                }
            }
        }

    }

    override fun showAllNotes(notes: List<NoteEntity>) {
        binding.emptyLay.visibility = View.GONE
        binding.noteList.visibility = View.VISIBLE

        noteAdapter.setData(notes)

        binding.noteList.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun showEmpty() {
        binding.emptyLay.visibility = View.VISIBLE
        binding.noteList.visibility = View.GONE
    }

    override fun deleteMessage() {
        Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}