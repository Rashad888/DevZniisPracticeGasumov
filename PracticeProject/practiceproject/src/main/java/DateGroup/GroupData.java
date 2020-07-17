package DateGroup;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.example.practiceproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DateGroup.DateConverter.String_date;

public class GroupData
{
    public SimpleExpandableListAdapter groupAddItems(List<String[]> rows , String[] childItems , ExpandableListView expView1 , Context context) //Метод заполняющий раскрывающийся список ExpanadablwListView
    {
        Map<String, String> map;

        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();// коллекция для групп

        for (String group : String_date(rows.get(0), Calendar.YEAR))// заполняем коллекцию групп из массива с названиями групп
        {
            // заполняем список атрибутов для каждой группы
            map = new HashMap<>();
            map.put("groupName", group); // время года
            groupDataList.add(map);
        }

        String groupFrom[] = new String[] { "groupName" };// список атрибутов групп для чтения

        int groupTo[] = new int[] { android.R.id.text1 };// список ID view-элементов, в которые будет помещены атрибуты групп

        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();// создаем общую коллекцию для коллекций элементов

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>


        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();// создаем коллекцию элементов для первой группы

        for(int i = 0 ; i < String_date(rows.get(0),Calendar.YEAR).length; i++)
        {
            for (String month : childItems)
            { //заполняем список атрибутов для каждого элемента
                map = new HashMap<>();
                map.put("monthName", month); // название месяца
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);// добавляем в коллекцию коллекций
            сhildDataItemList = new ArrayList<>();// создаем коллекцию элементов для каждой группы
        }

        String childFrom[] = new String[] { "monthName" };// список атрибутов элементов для чтения

        int childTo[] = new int[] { android.R.id.text1 };// список ID view-элементов, в которые будет помещены атрибуты элементов

        SimpleExpandableListAdapter expandableListAdapter = new SimpleExpandableListAdapter(
                context, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        return expandableListAdapter;
    }



    //public
}
