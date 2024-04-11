package com.example.notescomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notescomposeapp.presentation.AddNoteScreen
import com.example.notescomposeapp.presentation.NoteScreen
import com.example.notescomposeapp.presentation.NoteState
import com.example.notescomposeapp.presentation.NoteViewModel
import com.example.notescomposeapp.ui.theme.NotesComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.ViewModelProvider


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: NoteViewModel







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        setContent {
            NotesComposeAppTheme {
                val state by mainViewModel.state.collectAsState(initial = NoteState())

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "NotesScreen") {

                    composable("NotesScreen") {
                        NoteScreen(
                            state = state,
                            navController = navController,
                            onEvent = mainViewModel::onEvent
                        )
                    }

                    composable("AddNoteScreen") {
                        AddNoteScreen(
                            state = state,
                            navController = navController,
                            onEvent = mainViewModel::onEvent
                        )
                    }

                }

            }
        }
    }
}

