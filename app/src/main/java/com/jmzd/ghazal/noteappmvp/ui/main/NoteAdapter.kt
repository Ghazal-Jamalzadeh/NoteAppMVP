package com.jmzd.ghazal.noteappmvp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.noteappmvp.R
import com.jmzd.ghazal.noteappmvp.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvp.databinding.ItemNotesBinding
import com.jmzd.ghazal.noteappmvp.utils.*
import javax.inject.Inject

class NoteAdapter @Inject constructor() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private lateinit var binding: ItemNotesBinding
    private lateinit var context: Context
    private var moviesList = emptyList<NoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(moviesList[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = moviesList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: NoteEntity) {
            binding.apply {
                titleTxt.text = item.title
                descTxt.text = item.desc
                //Priority
                when (item.priority) {
                    HIGH -> priorityColor.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
                    NORMAL -> priorityColor.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                    LOW -> priorityColor.setBackgroundColor(ContextCompat.getColor(context, R.color.aqua))
                }
                //Category
                when (item.category) {
                    HOME -> categoryImg.setImageResource(R.drawable.home)
                    WORK -> categoryImg.setImageResource(R.drawable.work)
                    EDUCATION -> categoryImg.setImageResource(R.drawable.education)
                    HEALTH -> categoryImg.setImageResource(R.drawable.healthcare)
                }
                //Menu
                menuImg.setOnClickListener {
                    val popupMenu = PopupMenu(context, it) //(context , view)
                    popupMenu.menuInflater.inflate(R.menu.menu_items, popupMenu.menu) // (menu list , default menu appearance  )
                    popupMenu.show()
                    //Click
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        //it : menuItem!
                        when (menuItem.itemId) {
                            R.id.itemEdit -> {
                                onPopUpMenuClicked?.let {
                                    it(item, EDIT)
                                }
                            }
                            R.id.itemDelete -> {
                                onPopUpMenuClicked?.let {
                                    it(item, DELETE)
                                }
                            }
                        }
                        /*حتما این return را باید داشته باشیم اینجا که کلیک را برمیگرداند و جزو ساختار اصلی این منو هاست*/
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
    }

    private var onPopUpMenuClicked: ((NoteEntity, String) -> Unit)? = null

    fun setOnListItemClickListener(listener: (NoteEntity, String) -> Unit) {
        onPopUpMenuClicked = listener
    }

    fun setData(data: List<NoteEntity>) {
        val moviesDiffUtil = NotesDiffUtils(moviesList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NotesDiffUtils(private val oldItem: List<NoteEntity>, private val newItem: List<NoteEntity>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}