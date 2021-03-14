package com.example.mcs_midexam2101701563_laylanurulafidati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spLayoutShow;
    FloatingActionButton ibAddNotes;
    TextView tv_titleShow, tv_dateShow;
    DBHelper dbHelper;
    RecyclerView rvNotesShow;
    NotesAdapter adapter;

    ArrayList<Notes> arrayNotes;
    ArrayList<Integer> array_id;

    ArrayList<String> array_spinner;
    ArrayAdapter<String> adapter_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spLayoutShow = (Spinner)findViewById(R.id.sp_layoutShow);
        rvNotesShow = (RecyclerView)findViewById(R.id.rv_notesShow);
        ibAddNotes = (FloatingActionButton) findViewById(R.id.fab_add);
        tv_titleShow = (TextView)findViewById(R.id.tv_titleShow);
        tv_dateShow = (TextView)findViewById(R.id.tv_dateShow);

        dbHelper = new DBHelper(this, DBHelper.TABLE_NAME_NOTES, null, DBHelper.DB_VERSION);

        arrayNotes = new ArrayList<>();
        array_id = new ArrayList<>();

        array_spinner = new ArrayList<>();
        String [] array_spinner = {"Show as List", "Show as Grid"};

        adapter_spinner = new ArrayAdapter<>(getApplicationContext(),R.layout.tv_spinner, array_spinner);
        spLayoutShow.setAdapter(adapter_spinner);

        spLayoutShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    adapter = new NotesAdapter(arrayNotes, view.getContext(), array_id);
                    rvNotesShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    rvNotesShow.setAdapter(adapter);
                }
                else {
                    adapter = new NotesAdapter(arrayNotes, view.getContext(), array_id);
                    rvNotesShow.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
                    rvNotesShow.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Cursor cursor = dbHelper.getAllData();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            String description = cursor.getString(3);
            String date = cursor.getString(4);

            Notes notes = new Notes(title, category, description, date);
            arrayNotes.add(notes);
            array_id.add(id);
        }

        adapter = new NotesAdapter(arrayNotes, this, array_id);
        rvNotesShow.setLayoutManager(new LinearLayoutManager(this));
        rvNotesShow.setAdapter(adapter);

        ibAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });
    }

    private void Add() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}