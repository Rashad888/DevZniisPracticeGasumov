package DateGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

    public static String[] String_date(String[] date , int time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date[] myDate = new Date[date.length];
        try
        {
            for(int i = 0 ; i < date.length ; i++)
            {
                myDate[i] = dateFormat.parse(date[i]);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return distinctDate(myDate , time);
    }

    private static String[] distinctDate(Date[] myDate , int time) //Метод убирающийся повторяющиеся элементы из списка дат
    {
        ArrayList<String> dateList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        for(int i = 0; i< myDate.length;i++) //В данном цикле вытаскиваем день/месяц/год из списка дат и добавляем в список
        {
            cal.setTime(myDate[i]);
            dateList.add(Integer.toString(cal.get(time)));
        }
        Object[] st = dateList.toArray();

        for(Object z : st) //Проверка на дубликат элементов списка
        {
            if(dateList.indexOf(z) != dateList.lastIndexOf(z))
                dateList.remove(dateList.lastIndexOf(z));
        }

        return dateList.toArray(new String[0]);
    }
}
