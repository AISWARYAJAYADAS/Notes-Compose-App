package com.example.notescomposeapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescomposeapp.data.Note
import com.example.notescomposeapp.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
    private var dao: NoteDao
) : ViewModel() {

    private var isSortedByDateAdded = MutableStateFlow(true)


    private var notes = isSortedByDateAdded.flatMapLatest { isSorted ->
        if (isSorted) {
            dao.getOrderedByDateAddedBy()
        } else {
            dao.getOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var _state = MutableStateFlow(NoteState())

    var state = combine(_state, isSortedByDateAdded, notes) { state, _, notes ->
        state.copy(
            notes = notes
        )
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {

                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }

            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = event.title,
                    description = event.description,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertNote(note = note)
                }
                // Resetting the state after saving the note
                _state.value = NoteState(
                    title = mutableStateOf(""),
                    description = mutableStateOf("")
                )
            }

            NotesEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }


        }
    }
}


