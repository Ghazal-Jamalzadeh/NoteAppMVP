package com.jmzd.ghazal.noteappmvp.ui.add

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.data.repository.add.AddNoteRepository
import com.jmzd.ghazal.noteappmvp.databinding.FragmentNoteBinding
import com.jmzd.ghazal.noteappmvp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), NoteContracts.View {

    //Binding
    private lateinit var binding: FragmentNoteBinding

    @Inject
    lateinit var entity: NoteEntity

    @Inject
    lateinit var repository: AddNoteRepository

    @Inject
    lateinit var presenter: NotePresenter

    //Other
//    private val presenter by lazy { NotePresenter(repository, this) }
    private lateinit var categoriesList: Array<String>
    private var category = ""
    private lateinit var prioritiesList: Array<String>
    private var priority = ""
    private var noteId = 0
    private var type = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        type = if (noteId > 0) {
            EDIT
        } else {
            NEW
        }

        binding.apply {
            //Close
            closeImg.setOnClickListener { this@NoteFragment.dismiss() }
            //Spinners
            categoriesSpinnerItems()
            prioritiesSpinnerItems()
            //Set default value
            if (type == EDIT) {
                presenter.detailNote(noteId)
            }
            //Save
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                //Entity
                entity.id = 0
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority
                presenter.saveNote(entity)
            }
        }
    }


    private fun categoriesSpinnerItems() {
        categoriesList = arrayOf(WORK, HOME, EDUCATION, HEALTH)
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, categoriesList)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter
        binding.categoriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    category = categoriesList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
    }

    private fun prioritiesSpinnerItems() {
        prioritiesList = arrayOf(HIGH, NORMAL, LOW)
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, prioritiesList)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    priority = prioritiesList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
    }

    override fun close() {
        this.dismiss()
    }

    override fun loadNote(note: NoteEntity) {
        if (this.isAdded) {
            requireActivity().runOnUiThread {
                binding.apply {
                    titleEdt.setText(note.title)
                    descEdt.setText(note.desc)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}