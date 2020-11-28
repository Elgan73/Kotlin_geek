package com.aisgorod.kotlin_geek.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.data.Note
import com.aisgorod.kotlin_geek.data.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment(R.layout.fragment_note) {

    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NoteViewModel(note) as T
            }
        }).get(
            NoteViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = viewModel.note?.title ?: getString(R.string.title_create_note)

        titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }
        bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
        }

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        const val NOTE_KEY = "Note"

        fun create(note: Note? = null): NoteFragment {

            val fragment = NoteFragment()
            val argument = Bundle()
            argument.putParcelable(NOTE_KEY, note)
            fragment.arguments = argument

            return fragment
        }
    }
}