package edu.iu.eribecke.project6

import com.google.firebase.database.Exclude

//initializing note attributes
data class Note(
    @get:Exclude
    var noteId: String = "",
    var noteName: String = "",
    var noteDescription: String = ""
)
