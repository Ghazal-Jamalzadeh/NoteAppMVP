package com.jmzd.ghazal.noteappmvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.noteappmvp.R
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.data.repository.main.MainRepository
import com.jmzd.ghazal.noteappmvp.databinding.ActivityMainBinding
import com.jmzd.ghazal.noteappmvp.ui.add.NoteFragment
import com.jmzd.ghazal.noteappmvp.utils.*
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
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initViews
        binding.apply {
            //Set action view
            setSupportActionBar(notesToolbar)
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
            //Filter
            /*دسترسی به منوهای داخل تولبار */
            notesToolbar.setOnMenuItemClickListener {
                // it : MenuItem!
                when (it.itemId) {
                    R.id.actionFilter -> {
                        filterByPriority()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }

    }

    /*هندل کردن سرچ*/
    /*آیتم های داخل تولبار در واقع آپشن منو محسوب میشن */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* موارد مربوط به منومون رو بهمون میده ولی اول باید منوی مورد نظرمون رو بهش بشناسونیم  */
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        /* دسترسی به آیتم های منو با ایدی */
        val search = menu.findItem(R.id.actionSearch)
        /*
        * زمانی که منو را میساختیم اومدیم خ زیر را در فابل منو اضافه کردیم
        * app:actionViewClass="androidx.appcompat.widget.SearchView"
        * و از سرچ ویو ارث بری کن که حالت باز و بسته شدن را بتونه داشته باشه
        * پس باید آیتم بالا را تبدیل به سرچ ویو کنیم که به مواردش دسترسی داشته باشیم
        * */
        val searchView = search.actionView as SearchView
        /* هینت قرار دادن برای سرچ ویو */
        searchView.queryHint = getString(R.string.search)
        /* انجام سرچ به ازای هر کاراکتر وارد شده توسط کاربر */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            /* زمانی که کاربر روی دکمه اینتر کیبورد کلیک میکنه */
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            /* به ازای تک تک حروفی که کاربر وارد میکنه */
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchNote(newText)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
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

    private fun filterByPriority() {
        /* همیشه بین کتابخانه ساپورت و androidX از androidX استفاده کنید */
        val builder = AlertDialog.Builder(this)

        val priories = arrayOf(ALL, HIGH, NORMAL, LOW)

        builder.setSingleChoiceItems(priories, selectedItem) { dialog, item ->
            when (item) {
                0 -> {
                    presenter.loadAllNotes()
                }
                in 1..3 -> {
                    presenter.filterNote(priories[item])
                }
            }

            selectedItem = item
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}