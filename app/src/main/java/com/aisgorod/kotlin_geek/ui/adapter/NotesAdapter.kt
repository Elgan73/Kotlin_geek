package com.aisgorod.kotlin_geek.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aisgorod.kotlin_geek.databinding.ItemNoteBinding
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.model.mapToColor

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}

class NotesAdapter(val noteHandler: (Note) -> Unit) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        parent: ViewGroup, private val binding: ItemNoteBinding =
            ItemNoteBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        private lateinit var currentNote: Note

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        fun bind(item: Note) {
            currentNote = item
            with(binding) {
                title.text = item.title
                body.text = item.plot
                if(item.colorInt == 0) {
                    root.setBackgroundColor(item.colorC.mapToColor(binding.root.context))
                } else {
                    root.setBackgroundColor(item.colorInt)
                }

                root.setOnClickListener(clickListener)
            }
        }
    }
}



