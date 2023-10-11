package edu.iu.eribecke.project6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotesViewModelFactory(private val noteId: Long,
                            private val dao: NoteDao)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(noteId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}