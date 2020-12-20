package no3ratii.mohammad.dev.app.notebook.model.repository

import androidx.lifecycle.LiveData
import no3ratii.mohammad.dev.app.notebook.model.Dao.NoteDao
import no3ratii.mohammad.dev.app.notebook.model.Note

class NoteRepository(var noteDao: NoteDao) {

    var readAllNote : LiveData<List<Note>> = noteDao.readAllNote()

    fun singleNote(id:Int): Note {
        return noteDao.singleNote(id)
    }

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    //delete all value in dataabase
    suspend fun deleteAllNote(){
        noteDao.deleteAllItemNote()
    }
}