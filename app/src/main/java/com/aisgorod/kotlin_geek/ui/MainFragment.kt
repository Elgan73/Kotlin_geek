package com.aisgorod.kotlin_geek.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.aisgorod.kotlin_geek.App
import com.aisgorod.kotlin_geek.presentation.NotesViewModel
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.presentation.LogoutDialog
import com.aisgorod.kotlin_geek.presentation.LogoutDialog.Companion.TAG
import com.aisgorod.kotlin_geek.presentation.ViewState
import com.aisgorod.kotlin_geek.ui.adapter.NotesAdapter
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.toolbar
import kotlinx.android.synthetic.main.fragment_note.*

class MainFragment : Fragment(R.layout.fragment_main) {


    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            NotesViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar)

        setHasOptionsMenu(true)

        val adapter = NotesAdapter {
            navigateToNote(it)
        }
        recyclerView.adapter = adapter

        viewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> adapter.submitList(it.notes)
                ViewState.EMPTY -> Unit
            }
        }

        fab.setOnClickListener {
            navigateToCreation()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == SCROLL_STATE_IDLE) {
                    fab.show()
                } else {
                    fab.hide()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_account_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitFrAccBtn -> LogoutDialog.createInstance().show(childFragmentManager, TAG)

        }
        return true
    }

    private fun navigateToNote(note: Note) {
        App.fragmentRouter.navigateTo(NoteFragment.create(note))
    }

    private fun navigateToCreation() {
        App.fragmentRouter.navigateTo(NoteFragment.create(null))
    }


}