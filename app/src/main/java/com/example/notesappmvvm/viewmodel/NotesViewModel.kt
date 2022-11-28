package com.example.notesappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notesappmvvm.model.Notes
import com.example.notesappmvvm.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(val repository: NotesRepository) : ViewModel() {

    fun getLowNotes(): LiveData<List<Notes>> = repository.getLowNotes()
    fun getMediumNotes(): LiveData<List<Notes>> = repository.getMediumNotes()
    fun getHighNotes(): LiveData<List<Notes>> = repository.getHighNotes()

    fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    fun addNotes(notes: Notes) = repository.insertNotes(notes)

    fun deleteNotes(id: Int) = repository.deleteNotes(id)

    fun updateNotes(notes: Notes) = repository.updateNotes(notes)

}