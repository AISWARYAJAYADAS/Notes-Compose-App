package com.example.notescomposeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    var dateAdded: Long,
    val testField: String // New field

)