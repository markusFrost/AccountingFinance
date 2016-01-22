package com.example.hello.com.myapplicationest1.Objects;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpUtils
{
        public static long getTimeMillsDay(long data)
        {
            String format;

            format = "dd MMMM yyyy"; //задаём формат когда обрубаем по месяцу

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

            Date time = new Date(data);

            Date dataResult;
            try {
                dataResult = simpleDateFormat.parse(simpleDateFormat.format(time));

                //String value = simpleDateFormat.format(dataResult.getTime());

                return dataResult.getTime();

            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            return 0;


        }

    public static String getTimeString(long data)
    {
        String format;
        // format = "dd MM yyyy";// адаем формат когда обрубаем по месяцу
        format = " dd MMMM yyyy"; //здесь пишешь нужный формат
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date time = new Date(data);
        Date dataresult;

        try {

            dataresult = simpleDateFormat.parse(simpleDateFormat.format(time));
            String value = simpleDateFormat.format(dataresult);
            if ( time.getTime() == 0)
            {
                return "Unknown";
            }
            else
            {
                return value;
            }
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }//
        return null;
    }

    public static  String formatName(String name)
    {
        return  name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

}
