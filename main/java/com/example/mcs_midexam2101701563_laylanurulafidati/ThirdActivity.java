package com.example.mcs_midexam2101701563_laylanurulafidati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThirdActivity extends AppCompatActivity {
    EditText et_title2, et_category2, et_description2;
    Button btn_save2;
    DBHelper dbHelper;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        et_title2 = (EditText)findViewById(R.id.et_title2);
        et_category2 = (EditText)findViewById(R.id.et_category2);
        et_description2 = (EditText)findViewById(R.id.et_description2);
        btn_save2 = (Button) findViewById(R.id.btn_save2);

        dbHelper = new DBHelper(this, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);

        id = getIntent().getIntExtra("id", 0);

        btn_save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUpdate();
            }
        });
    }

    private void validateUpdate() {
        String editTitle, editCategory, editDescription, currentDate;
        int day, month, year;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;

        editTitle = et_title2.getText().toString();
        editCategory = et_category2.getText().toString();
        editDescription = et_description2.getText().toString();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        dateFormat = new SimpleDateFormat(day+"-"+(month+1)+"-"+year);
        currentDate = dateFormat.format(calendar.getTime());

        if (editTitle.isEmpty()) {
            Toast.makeText(this, "Title must be filled", Toast.LENGTH_LONG).show();
        }
        else if (editCategory.isEmpty()) {
            Toast.makeText(this, "Category must be filled", Toast.LENGTH_LONG).show();
        }
        else if (editDescription.length() < 20) {
            Toast.makeText(this, "Description must be filled at least 20 characters ", Toast.LENGTH_LONG).show();
        }
        else {
            Notes notes = new Notes(editTitle, editCategory, editDescription, currentDate);
            dbHelper.dbUpdate(id, notes);
            Toast.makeText(this, editTitle + ", " + editCategory + ", " + editDescription, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}