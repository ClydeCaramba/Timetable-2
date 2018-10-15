package com.example.caramba.chrttimetable;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Timetable.EXTRA_MESSAGE);

        TextView textView = new TextView(this);
        textView.setTextSize(50);
        textView.setText(message);
        setContentView(textView);
    }

    public void parseSite() throws IOException
    {
        int ColOfGroupNumber = 0;

        Document document = Jsoup.connect("https://radiotech.su/students/fulltimedepartment/Timetable-of-classes/").get();
        Element table = document.select("table").get(1);
        Elements rows = table.select("tr");
        Elements cols = rows.get(0).select("td");
        for (int i = 0; i < cols.size(); i++)
        {
            if (cols.get(i).select("p").select("strong").get(1).toString() == "319")
            {
                ColOfGroupNumber = i;
                break;
            }
        }

    }
}
