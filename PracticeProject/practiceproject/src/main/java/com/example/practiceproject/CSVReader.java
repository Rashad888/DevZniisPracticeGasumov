package com.example.practiceproject;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    Context context;
    String filename;
    List<String[]> rows = new ArrayList<>();

    public CSVReader(Context context, String fileName) { //Конструктор класса CSVReader
        this.context = context;
        this.filename = fileName;
    }

    public List<String[]> readCSV() throws IOException {
        InputStream is = context.getAssets().open(filename);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ";";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            rows.add(row);
        }
        return rows;
    }
}
