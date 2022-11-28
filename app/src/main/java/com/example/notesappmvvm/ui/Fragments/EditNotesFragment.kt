package com.example.notesappmvvm.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.notesappmvvm.R
import com.example.notesappmvvm.databinding.FragmentEditNotesBinding
import com.example.notesappmvvm.model.Notes
import com.example.notesappmvvm.viewmodel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditNotesFragment : Fragment() {
   private lateinit var binding: FragmentEditNotesBinding
    private var priority: String = "1"
   private val viewModel: NotesViewModel by viewModels()
    val oldnotes by navArgs<EditNotesFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding.etTitle.setText(oldnotes.notes.title)
        binding.etSubtitle.setText(oldnotes.notes.subtitle)
        binding.etNotes.setText(oldnotes.notes.notes)

        when (oldnotes.notes.priority) {
            "1" -> {
                priority = "1"
                binding.ivGreen.setImageResource(R.drawable.done2)
                binding.ivRed.setImageResource(R.color.red)
                binding.ivYellow.setImageResource(R.color.yellow)
            }
            "2" -> {
                priority = "2"
                binding.ivYellow.setImageResource(R.drawable.done2)
                binding.ivRed.setImageResource(R.color.red)
                binding.ivGreen.setImageResource(R.color.green)
            }

            "3" -> {
                priority = "3"
                binding.ivRed.setImageResource(R.drawable.done2)
                binding.ivGreen.setImageResource(R.color.green)
                binding.ivYellow.setImageResource(R.color.yellow)
            }
        }

        binding.ivGreen.setOnClickListener {
            priority = "1"
            binding.ivGreen.setImageResource(R.drawable.done2)
            binding.ivRed.setImageResource(R.color.red)
            binding.ivYellow.setImageResource(R.color.yellow)
        }

        binding.ivRed.setOnClickListener {
            priority = "3"
            binding.ivRed.setImageResource(R.drawable.done2)
            binding.ivGreen.setImageResource(R.color.green)
            binding.ivYellow.setImageResource(R.color.yellow)
        }

        binding.ivYellow.setOnClickListener {
            priority = "2"
            binding.ivYellow.setImageResource(R.drawable.done2)
            binding.ivRed.setImageResource(R.color.red)
            binding.ivGreen.setImageResource(R.color.green)
        }

        binding.btnSave.setOnClickListener {
            updateNotes(it)
        }
        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.etTitle.text
        val subtitle = binding.etSubtitle.text
        val notes = binding.etNotes.text
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM,d,yyyy", d.time)

        val data = Notes(
            oldnotes.notes.id,
            title = title.toString(),
            subtitle = subtitle.toString(),
            notes = notes.toString(),
            date = notesDate.toString(),
            priority
        )

        viewModel.updateNotes(data)
        Toast.makeText(requireContext(), "Notes updated successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
// working on itemclick on toolbar icons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val tvYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val tvNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            tvYes?.setOnClickListener {
                viewModel.deleteNotes(oldnotes.notes.id!!)
                bottomSheet.dismiss()
                requireActivity().onBackPressed()
            }

            tvNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()

        }else if(item.itemId==android.R.id.home){
            requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}