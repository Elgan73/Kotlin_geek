package com.aisgorod.kotlin_geek.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aisgorod.kotlin_geek.presentation.NotesViewModel
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.presentation.ViewState
import com.aisgorod.kotlin_geek.ui.adapter.NotesAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            NotesViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NotesAdapter()
        recyclerView.adapter = adapter

        viewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> adapter.submitList(it.notes)
                ViewState.EMPTY -> Unit
            }
        }
    }
}