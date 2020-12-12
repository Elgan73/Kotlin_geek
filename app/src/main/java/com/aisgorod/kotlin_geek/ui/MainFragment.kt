package com.aisgorod.kotlin_geek.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.aisgorod.kotlin_geek.App
import com.aisgorod.kotlin_geek.presentation.NotesViewModel
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.databinding.FragmentMainBinding
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.presentation.LogoutDialog
import com.aisgorod.kotlin_geek.presentation.LogoutDialog.Companion.TAG
import com.aisgorod.kotlin_geek.presentation.ViewState
import com.aisgorod.kotlin_geek.ui.adapter.NotesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModel<NotesViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? MainActivity)?.setSupportActionBar(binding.toolbar)

        setHasOptionsMenu(true)

        val adapter = NotesAdapter {
            navigateToNote(it)
        }
        binding.recyclerView.adapter = adapter

        viewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> adapter.submitList(it.notes)
                ViewState.EMPTY -> Unit
            }
        }

        binding.fab.setOnClickListener {
            navigateToCreation()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == SCROLL_STATE_IDLE) {
                    binding.fab.show()
                } else {
                    binding.fab.hide()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToNote(note: Note) {
        App.fragmentRouter.navigateTo(NoteFragment.create(note))
    }

    private fun navigateToCreation() {
        App.fragmentRouter.navigateTo(NoteFragment.create(null))
    }


}