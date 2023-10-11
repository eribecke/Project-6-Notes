package edu.iu.eribecke.project6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(noteId: Long, val dao: NoteDao) : ViewModel() {
    val note = dao.get(noteId)
    var newNoteName = ""
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList
    fun updateNote() {
        viewModelScope.launch {
            dao.update(note.value!!)
            _navigateToList.value = true
        }
    }
    fun deleteNote() {
        viewModelScope.launch {
            dao.delete(note.value!!)
            _navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}