package com.example.notesappsql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notesappsql.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        onCreate(db);
    }

    public long insertNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getDescription());

        long id = db.insert(Note.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        final String GET_ALL_NOTES_QUERY = "SELECT * FROM " + Note.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(GET_ALL_NOTES_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID));
                String date = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DATE));
                String desc = cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE));
                Note note = new Note(id, desc, date);
                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return notes;
    }
}
