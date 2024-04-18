package com.example.notescomposeapp.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
           // db.execSQL("ALTER TABLE Note ADD COLUMN testField STRING  NOT NULL ''")
          //  db.execSQL("ALTER TABLE Note ADD COLUMN testField TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE Note ADD COLUMN testField TEXT")


        }
    }
}