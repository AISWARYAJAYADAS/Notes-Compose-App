package com.example.notescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notescomposeapp.data.NoteDatabase
import com.example.notescomposeapp.presentation.AddNoteScreen
import com.example.notescomposeapp.presentation.NoteScreen
import com.example.notescomposeapp.presentation.NoteState
import com.example.notescomposeapp.presentation.NoteViewModel
import com.example.notescomposeapp.ui.theme.NotesComposeAppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesComposeAppTheme {
                val state by viewModel.state.collectAsState(initial = NoteState())
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "NotesScreen") {

                    composable("NotesScreen") {
                        NoteScreen(
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }

                    composable("AddNoteScreen") {
                        AddNoteScreen(
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }

                }

            }
        }
    }
}

