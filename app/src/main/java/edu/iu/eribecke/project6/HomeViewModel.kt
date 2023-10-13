package edu.iu.eribecke.project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(val dao: NoteDao) : ViewModel() {
    //get all items from dao
    val notes = dao.getAll()

    //initializing variable for handling note navigation
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote

    //triggers note navigation when note is clicked
    fun onNoteClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }

    //deletes note
    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            val note = Note()
            note.noteId = noteId
            dao.delete(note)

        }
    }

    //navigates to note when add button is clicked
    fun onAddButtonClicked(){
        _navigateToNote.value = 10000
    }

    //resets _navigateToNote value after fragment transition
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
}