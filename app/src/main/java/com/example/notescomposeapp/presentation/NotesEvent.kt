package com.example.notescomposeapp.presentation

import com.example.notescomposeapp.data.Note

sealed interface NotesEvent {
    data object SortNotes : NotesEvent
    data class DeleteNote(var note: Note) : NotesEvent
    data class SaveNote(
        var title: String,
        var description: String,
        var testField : String
    ) : NotesEvent
}