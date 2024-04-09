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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesComposeAppTheme {
                val viewModel: NoteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)
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

