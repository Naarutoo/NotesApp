package com.example.notesappmvvm.ui.Fragments


import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notesappmvvm.R
import com.example.notesappmvvm.databinding.FragmentCreateNotesBinding
import com.example.notesappmvvm.model.Notes
import com.example.notesappmvvm.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateNotesFragment : Fragment() {

    lateinit var binding: FragmentCreateNotesBinding
    var priority: String = "1"
    val viewmodel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)
        binding.ivGreen.setImageResource(R.drawable.done2)

        binding.ivGreen.setOnClickListener {
            priority = "1"
            binding.ivGreen.setImageResource(R.drawable.done2)
            binding.ivRed.setImageResource(0)
            binding.ivYellow.setImageResource(0)
        }

        binding.ivRed.setOnClickListener {
            priority = "3"
            binding.ivRed.setImageResource(R.drawable.done2)
            binding.ivGreen.setImageResource(0)
            binding.ivYellow.setImageResource(0)
        }

        binding.ivYellow.setOnClickListener {
            priority = "2"
            binding.ivYellow.setImageResource(R.drawable.done2)
            binding.ivRed.setImageResource(0)
            binding.ivGreen.setImageResource(0)
        }

        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

//        binding.


        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.etTitle.text
        val subtitle = binding.etSubtitle.text
        val notes = binding.etNotes.text
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM,d,yyyy", d.time)

        val data = Notes(
            null,
            title = title.toString(),
            subtitle = subtitle.toString(),
            notes = notes.toString(),
            date = notesDate.toString(),
            priority
        )

        viewmodel.addNotes(data)
        Toast.makeText(requireContext(), "Notes created successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }

}