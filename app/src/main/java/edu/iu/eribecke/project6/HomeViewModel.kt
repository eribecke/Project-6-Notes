package edu.iu.eribecke.project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(val dao: NoteDao) : ViewModel() {
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote

    fun onNoteClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }
    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            val note = Note()
            note.noteId = noteId
            dao.delete(note)
            Log.d("get wrekted", "note")

        }
    }
    fun onAddButtonClicked(){
        _navigateToNote.value = 10000
    }

    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
}