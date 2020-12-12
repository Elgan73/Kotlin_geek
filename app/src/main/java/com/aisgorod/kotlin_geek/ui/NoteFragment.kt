package com.aisgorod.kotlin_geek.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.databinding.FragmentNoteBinding
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.presentation.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import petrov.kristiyan.colorpicker.ColorPicker

class NoteFragment : Fragment() {

    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by viewModel<NoteViewModel> {
        parametersOf(note)
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? MainActivity)?.setSupportActionBar(binding.toolbar)

        setHasOptionsMenu(true)

        viewModel.note?.let {
            binding.titleEt.setText(it.title)
            binding.bodyEt.setText(it.plot)
        }

        viewModel.showError().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Error while save note!", Toast.LENGTH_LONG).show()
        }

        binding.toolbar.title = viewModel.note?.title ?: getString(R.string.title_create_note)

        binding.titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }
        binding.bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
        }

        binding.toolbar.setNavigationOnClickListener {
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
            R.id.deleteNoteBtn -> {
                viewModel.deleteNote()
                activity?.onBackPressed()
            }
            R.id.paletteBtn -> {
                openColorPicker()

            }
        }
        return true
    }

    private fun openColorPicker() {
        val colP = ColorPicker(activity)
        colP.show()
        colP.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                binding.toolbar.setBackgroundColor(color)
                viewModel.updateColor(color)
            }

            override fun onCancel() {
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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