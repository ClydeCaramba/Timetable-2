package com.example.caramba.chrttimetable;

import android.content.Intent;
import android.os.AsyncTask;
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

    private String resultTimeTable;
    TextView textViewRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Timetable.EXTRA_MESSAGE);

        TextView textView = new TextView(this);
        textView.setTextSize(50);
       // textViewRes = findViewById(R.id.textViewResult);
       // textViewRes.setText("Test string!");
        //textView.setText(message);
        String parseOutput = "";
        //parseSite parseMachine = new parseSite();
        //textViewRes.setText("Test string!");
       // parseMachine.execute();
       // textView.setText(parseOutput);
        parseOutput = "";
        parseOutput = "";
        parseOutput = "";
        parseOutput = "";
        parseOutput = "";
        parseOutput = "";
        parseOutput = "";
       // TextView textView1 = findViewById(R.id.textViewResult);
        setContentView(R.layout.activity_display_message);
        updateData();
    }
    protected void updateData()
    {
        textViewRes = findViewById(R.id.textViewResult);
        parseSite parseMachine = new parseSite();
        parseMachine.execute();
    }

    /*public void parseSite(String output) throws IOException
    {
        int ColOfGroupNumber = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://radiotech.su/students/fulltimedepartment/Timetable-of-classes/").get();
                }
                catch (IOException t)
                {}
            }
        }).run();

        Element table = Globals.doc.select("table").get(1);
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
        for (int i = 1; i < rows.size(); i++)
        {
            if (rows.get(i).select("td").get(ColOfGroupNumber).select("em").get(0).toString() != "&nbsp;")
            {
                output += rows.get(i).select("td").get(ColOfGroupNumber).select("em").get(0).toString() + "\n";
                output += rows.get(i).select("td").get(ColOfGroupNumber).select("em").get(1).toString() + "\n";
            }

        }

    }*/

    class parseSite extends AsyncTask <Void, Void, Void>
    {
        private String resultStr = "";
        private int rowCounter = 12;
        @Override
        protected Void doInBackground(Void... params)
        {
            int ColOfGroupNumber = 0;
            Document doc = null;
            try {
                doc = Jsoup.connect("https://radiotech.su/students/fulltimedepartment/Timetable-of-classes/").get();
            }
            catch (IOException t)
            {
                t.printStackTrace();
            }
            Element table = doc.select("table").get(1);
            Elements rows = table.select("tr");
            Elements cols = rows.get(0).select("td");
            for (int i = 0; i < cols.size(); i++)
            {
                if (cols.get(i).select("p").select("strong").size() != 1) {
                    if (cols.get(i).select("p").select("strong").get(1).text().equals("319")) {
                        ColOfGroupNumber = i;
                        break;
                    }
                }
            }
            for (int i = 1; i < 13; i++)
            {
                if (rows.get(i).select("td").size() > 2)
                    if (rows.get(i).select("td").get(ColOfGroupNumber).select("em").size() != 0 || rows.get(i).select("td").get(ColOfGroupNumber - 1).select("p").size() != 0) {
                        if (rows.get(i).select("td").get(ColOfGroupNumber).select("p").get(0).text().length() > 1 && (i % 2 != 0)) {
                            resultStr += rows.get(i).select("td").get(ColOfGroupNumber).select("em").get(0).text() + " ";
                            if (rows.get(i).select("td").get(ColOfGroupNumber).select("em").size() != 1)
                                resultStr += rows.get(i).select("td").get(ColOfGroupNumber).select("em").get(1).text() + "\n";
                        }
                        if (rows.get(i).select("td").get(ColOfGroupNumber - 1).select("p").text().length() > 1 && (i % 2 == 0)) {
                            resultStr += rows.get(i).select("td").get(ColOfGroupNumber - 1).select("p").get(0).text() + "\n" + "\n";
                        }
                    }

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            resultTimeTable = resultStr;
            textViewRes.setText(resultStr);
        }
    }
}
