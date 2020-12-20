package no3ratii.mohammad.dev.app.notebook.model.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import no3ratii.mohammad.dev.app.notebook.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note:Note)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun readAllNote(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = :itemId")
    fun singleNote(itemId: Int): Note

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE  FROM note_table")
    suspend fun deleteAllItemNote()

}