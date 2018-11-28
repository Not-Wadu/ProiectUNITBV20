package com.example.michael.proiectunitbv;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GoogleCalendar extends AppCompatActivity {

    EditText text_linkcalendar;
    Button copy;


    //still not working, CE NAIBA AIII
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_calendar);

       copy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                ClipboardManager Clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",text_linkcalendar.getText().toString());
                Clipboard.setPrimaryClip(clip);
               Toast.makeText(GoogleCalendar.this, "LinkCopied", Toast.LENGTH_SHORT).show();
           }
       });



    }
    }



