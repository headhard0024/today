package no3ratii.mohammad.dev.app.notebook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no3ratii.mohammad.dev.app.notebook.base.connection.DataBaseConnection
import no3ratii.mohammad.dev.app.notebook.model.Note
import no3ratii.mohammad.dev.app.notebook.model.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllNote: LiveData<List<Note>>
    private val noteRepository: NoteRepository

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

    fun singleNote(id: Int): Note {
        return noteRepository.singleNote(id)
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun deleteAllNote() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAllNote()
        }
    }

    init {
        val noteDao = DataBaseConnection.getDatabase(application.applicationContext).noteDao()
        noteRepository = NoteRepository(noteDao)
        readAllNote = noteRepository.readAllNote
    }

}