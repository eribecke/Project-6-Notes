package edu.iu.eribecke.project6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class HomeViewModel() : ViewModel() {
    //get all items from dao

    var noteId : String = ""
    var note = MutableLiveData<Note>()
    private val _notes : MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>>
        get() = _notes as LiveData<List<Note>>
    //initializing variable for handling note navigation
    private val _navigateToNote = MutableLiveData<String?>()
    val navigateToNote: LiveData<String?>
        get() = _navigateToNote
    val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    private lateinit var notesCollection: DatabaseReference

    init{
        if(noteId.trim() == ""){
            note.value = Note()
        }
        val database = Firebase.database
        _notes.value = mutableListOf<Note>()
        notesCollection = database.getReference("notes")
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val note = dataSnapshot.getValue<Note>()
                _notes.value!!.add(note!!)
            }


            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val newNote = dataSnapshot.getValue<Note>()
                val noteKey = dataSnapshot.key
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot){
                val noteKey = dataSnapshot.key
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?){
                val movedTask = dataSnapshot.getValue<Note>()
                val noteKey = dataSnapshot.key
            }
            override fun onCancelled(databaseError: DatabaseError){

            }
        }

        notesCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                var notesList : ArrayList<Note> = ArrayList()
                for(noteSnapshot in dataSnapshot.children){
                    var note = noteSnapshot.getValue<Note>()
                    note?.noteId = noteSnapshot.key!!
                    notesList.add(note!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError){

            }
        })

    }
    //triggers note navigation when note is clicked
    fun onNoteClicked(chosenNote: Note) {
        _navigateToNote.value = chosenNote.noteId
        noteId = chosenNote.noteId
        note.value = chosenNote
    }

    //deletes note
    fun deleteNote(noteId: String) {

        notesCollection.child(noteId).removeValue()
    }

    //navigates to note when add button is clicked
    fun onAddButtonClicked(){
        _navigateToNote.value = "new"
    }

    fun updateNote() {

        if(noteId.trim() == ""){
            Log.d("yes", "bruh")
            notesCollection.push().setValue(note.value)
        }
        else{
            notesCollection.child(noteId).setValue(note.value)
        }
        _navigateToList.value = true
    }

    //resets _navigateToNote value after fragment transition
    fun onNoteNavigated() {
        _navigateToNote.value = null
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}