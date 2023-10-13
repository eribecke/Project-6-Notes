package edu.iu.eribecke.project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(noteId: Long, val dao: NoteDao) : ViewModel() {

    //initializing variables
    var note = MutableLiveData<Note>()
    val noteId : Long = noteId
    private val _navigateToHome = MutableLiveData<Boolean>(false)
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    //initializes note value based off whether it is a new note or not
    init {
        dao.get(noteId).observeForever{it ->
        if(it==null){
            note.value = Note()
        }
        else{
            note.value = it
        }
        }
    }

    //updates the values of the note
    fun updateNote() {
        viewModelScope.launch {
            if(note.value?.noteId != 0L){
                dao.update(note.value!!)
            }
            else{
                dao.insert(note.value!!)
            }
            _navigateToHome.value = true
        }
    }


    //resets _navigateToHome value after transitioning back to home fragment
    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }
}