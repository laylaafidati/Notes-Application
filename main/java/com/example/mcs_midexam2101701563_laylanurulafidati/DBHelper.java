package com.example.mcs_midexam2101701563_laylanurulafidati;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "NotesDB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME_NOTES = "Notes";
    public static final String FIELD_NOTES_ID = "id";
    public static final String FIELD_NOTES_TITLE = "title";
    public static final String FIELD_NOTES_CATEGORY = "category";
    public static final String FIELD_NOTES_DESCRIPTION = "description";
    public static final String FIELD_NOTES_DATE = "date";

    private static final String createNotes =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_NOTES + " (" +
                    FIELD_NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FIELD_NOTES_TITLE + " TEXT," +
                    FIELD_NOTES_CATEGORY + " TEXT," +
                    FIELD_NOTES_DESCRIPTION + " TEXT," +
                    FIELD_NOTES_DATE + " TEXT)";

    public static final String dropNotes =
            "DROP TABLE IF EXISTS " + TABLE_NAME_NOTES;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public boolean dbInsert(Notes notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_NOTES_TITLE, notes.title);
        cv.put(FIELD_NOTES_CATEGORY, notes.category);
        cv.put(FIELD_NOTES_DESCRIPTION, notes.description);
        cv.put(FIELD_NOTES_DATE, notes.date);
        long result = db.insert(TABLE_NAME_NOTES, null, cv);
        db.close();
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData() {
        String query = "SELECT * FROM " + TABLE_NAME_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void dbUpdate(int id, Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_NOTES_ID, id);
        cv.put(FIELD_NOTES_TITLE, notes.title);
        cv.put(FIELD_NOTES_CATEGORY, notes.category);
        cv.put(FIELD_NOTES_DESCRIPTION, notes.description);
        cv.put(FIELD_NOTES_DATE, notes.date);
        db.update(TABLE_NAME_NOTES, cv, "id = ?", new String[] {""+id});
    }

    public void dbDelete (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_NOTES,FIELD_NOTES_ID+"="+id, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createNotes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropNotes);
        onCreate(db);
    }
}
