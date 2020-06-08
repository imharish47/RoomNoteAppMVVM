package com.company47.roomnoteappmvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.company47.roomnoteappmvvm.R;

import java.util.Objects;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITlE = "com.company47.roomnoteappmvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.company47.roomnoteappmvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.company47.roomnoteappmvvm.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.company47.roomnoteappmvvm.ID";

    private EditText mEditText_Title, mEditText_desc;
    private NumberPicker numberPicker_Priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        mEditText_Title = findViewById(R.id.edit_text_title);
        mEditText_desc = findViewById(R.id.edit_text_description);
        numberPicker_Priority = findViewById(R.id.number_picker_priority);

        numberPicker_Priority.setMaxValue(10);
        numberPicker_Priority.setMinValue(1);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            //editMode
            setTitle("Edit Note");

            mEditText_Title.setText(intent.getStringExtra(EXTRA_TITlE));
            mEditText_desc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker_Priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

        } else {
            //add mode
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    private void saveNote() {
        String title = mEditText_Title.getText().toString().trim();
        String desc = mEditText_desc.getText().toString().trim();
        int priority = numberPicker_Priority.getValue();


        if (!title.equals("") && !desc.equals("")) {
            Intent data = new Intent();
            data.putExtra(EXTRA_TITlE, title);
            data.putExtra(EXTRA_DESCRIPTION, desc);
            data.putExtra(EXTRA_PRIORITY, priority);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            finish();

        } else {
            Toast.makeText(this, "Empty title or description", Toast.LENGTH_SHORT).show();

        }


    }

}