package com.aisgorod.kotlin_geek.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.presentation.NoteViewModel
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

        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        viewModel.note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.plot)
        }

        viewModel.showError().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Error while save note!", Toast.LENGTH_LONG).show()
        }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveNoteBtn -> {
                viewModel.saveNote()
                activity?.onBackPressed()
            }
            R.id.deleteNote -> {
                viewModel.deleteNote()
                activity?.onBackPressed()
            }
        }
        return true
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