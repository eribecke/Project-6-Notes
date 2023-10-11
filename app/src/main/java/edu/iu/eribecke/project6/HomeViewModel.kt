package edu.iu.eribecke.project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(val dao: NoteDao) : ViewModel() {
    var newNoteName = ""
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToNote: LiveData<Long?>
        get() = _navigateToNote

    fun onNoteClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }

    fun onAddButtonClicked(){
        Log.d("please", "work")
        _navigateToNote.value = 10000
    }

    fun onNoteNavigated() {
        _navigateToNote.value = null
    }
}