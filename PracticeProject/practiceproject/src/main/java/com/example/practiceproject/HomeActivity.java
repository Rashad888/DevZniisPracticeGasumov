package com.example.practiceproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DateGroup.GroupData;

public class HomeActivity extends AppCompatActivity {

    ListView dataView; //Список ListView куда выводим данные
    List<String[]> rows; //Список из массива строк, содержащий все данные
    ExpandableListView expView1; //Раскрывающийся список (Содержит года)
    Button showDataBtn;
    GroupData groupData = new GroupData();

    private String[] months = new String[] {"Сентябрь","Октябрь","Ноябрь","Декабрь","Январь","Февраль","Март","Апрель", "Май","Июнь","Июль","Август"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        showDataBtn = findViewById(R.id.resBtn);
        dataView = findViewById(R.id.listView);
        rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader(HomeActivity.this,"practiceData.csv");
        try
        {
            rows = csvReader.readCSV();
        }
        catch (IOException e)
        {
            Toast.makeText(getApplicationContext(),"Ошибка при чтении файла!",Toast.LENGTH_LONG).show();
        }


        expView1 = (ExpandableListView) findViewById(R.id.expandableListView1);
        expView1.setAdapter(groupData.groupAddItems(rows , months , expView1 , this));//Задаем адаптер ExpanadableListView

        MyAdapter adapter = new MyAdapter(HomeActivity.this , rows);
        dataView.setAdapter(adapter); //Задаем адаптер ListView

        expView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                checkRow(groupPosition , childPosition);

                return true;
            }

        });

        showDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAdapter adapter = new MyAdapter(HomeActivity.this , rows);
                dataView.setAdapter(adapter); //Задаем адаптер ListView
            }
        });
    }

    private void checkRow(int groupPos , int childPos) //Метод, который выводит данные по выбранному году и месяцу
    {
        String[] date = new String[rows.get(0).length];
        String[] countOrder = new String[rows.get(1).length];
        String[] countTransf = new String[rows.get(2).length];
        String selectedDate = getChildItem(groupPos , childPos) + "." + getGroupItem(groupPos); //Локальная переменная, хранящая выбранную строку месяц.год
        List<String[]> dataList = new ArrayList<>();
        for(int i = 0 ; i < rows.get(0).length ; i++)
        {
            if (rows.get(0)[i].contains(selectedDate))
            {
                date[i] = rows.get(0)[i];
                countOrder[i] = rows.get(1)[i];
                countTransf[i] = rows.get(2)[i];
            }
        }
        dataList.add(date); dataList.add(countOrder) ; dataList.add(countTransf);
        MyAdapter adapter = new MyAdapter(this , dataList);
        dataView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String getGroupItem(int i) //Получаем текст родительский элемента ExpandableListView
    {
        return expView1.getExpandableListAdapter()
                .getGroup(i).toString()
                .replaceAll("[\\[\\](){}]","")
                .replace("groupName=","");
    }

    private String getChildItem(int i , int j) //Получаем текст дочернего элемента ExpandableListView
    {
        String monName = expView1.getExpandableListAdapter()
                .getChild(i,j).toString()
                .replaceAll("[\\[\\](){}]","")
                .replace("monthName=","");
        String monthId = "";
        switch (monName)
        {
            case ("Январь"):
                monthId = "01";
                break;
            case ("Февраль"):
                monthId = "02";
                break;
            case ("Март"):
                monthId = "03";
                break;
            case ("Апрель"):
                monthId = "04";
                break;
            case ("Май"):
                monthId = "05";
                break;
            case ("Июнь"):
                monthId = "06";
                break;
            case ("Июль"):
                monthId = "07";
                break;
            case ("Август"):
                monthId = "08";
                break;
            case ("Сентябрь"):
                monthId = "09";
                break;
            case ("Октябрь"):
                monthId = "10";
                break;
            case ("Ноябрь"):
                monthId = "11";
                break;
            case ("Декабрь"):
                monthId = "12";
                break;


        }

        return monthId;
    }


    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String rDate[];
        String[] rCountOfRequests;
        String rCountOfTransfers[];

        MyAdapter (Context c , List<String[]> outputList)
        {
            super(c,R.layout.row,R.id.dateInfoTxt , outputList.get(0));
            this.context = c;
            this.rDate = outputList.get(0);
            this.rCountOfRequests = outputList.get(1);
            this.rCountOfTransfers = outputList.get(2);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent , false);

            //Лейблы, куда выводятся данные
            TextView dateV = row.findViewById(R.id.dateInfoTxt);
            TextView reqV = row.findViewById(R.id.requestInfoTxt);
            TextView transfV = row.findViewById(R.id.transfInfoTxt);

            //Устанавливаем значения
            dateV.setText(rDate[position]);
            reqV.setText(rCountOfRequests[position]);
            transfV.setText(rCountOfTransfers[position]);

            TextView dateText = row.findViewById(R.id.dateTxt);
            TextView reqText = row.findViewById(R.id.requestTxt);
            TextView tranText = row.findViewById(R.id.transferTxt);
            if (dateV.getText().toString() == "")
            {
                dateV.setText(null); reqV.setText(null); transfV.setText(null);
                dateText.setText(null); reqText.setText(null); tranText.setText(null);
            }
            return row;
        }
    }
}