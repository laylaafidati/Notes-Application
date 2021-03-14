package com.example.mcs_midexam2101701563_laylanurulafidati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {
    EditText et_title, et_category, et_description;
    Button btn_save;
    DBHelper dbHelper;
    ArrayList<Notes> array_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        et_title = (EditText)findViewById(R.id.et_title);
        et_category = (EditText)findViewById(R.id.et_category);
        et_description = (EditText)findViewById(R.id.et_description);
        btn_save = (Button) findViewById(R.id.btn_save);

        dbHelper = new DBHelper(this, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSave();
            }
        });
    }

    private void validateSave() {
        String inputTitle, inputCategory, inputDescription, currentDate;
        int day, month, year;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;

        inputTitle = et_title.getText().toString();
        inputCategory = et_category.getText().toString();
        inputDescription = et_description.getText().toString();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        dateFormat = new SimpleDateFormat(day+"-"+(month+1)+"-"+year);
        currentDate = dateFormat.format(calendar.getTime());

        if (inputTitle.isEmpty()) {
            Toast.makeText(this, "Title must be filled", Toast.LENGTH_LONG).show();
        }
        else if (inputCategory.isEmpty()) {
            Toast.makeText(this, "Category must be filled", Toast.LENGTH_LONG).show();
        }
        else if (inputDescription.length() < 20) {
            Toast.makeText(this, "Description must be filled at least 20 characters ", Toast.LENGTH_LONG).show();
        }
        else {
            Notes notes = new Notes(inputTitle, inputCategory, inputDescription, currentDate);
            boolean add = dbHelper.dbInsert(notes);
            if (add == true) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}