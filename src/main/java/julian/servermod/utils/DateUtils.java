package julian.servermod.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {
    public static final String FORMAT = "yyyy-MM-dd";

    public static int durationBetweenDayAndToday(String dayInString) {
        Date parsedDay = parseDateFromString(dayInString);
        Date today = parseDateFromString(getTodayDateAsString());
        long duration = calculateDayDifference(parsedDay, today);
        return (int) duration;
    }


    public static String getTodayDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        Date today = new Date();
        return sdf.format(today);
    }

    public static Date parseDateFromString(String dateString)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long calculateDayDifference(Date date1, Date date2) {
        LocalDate localDate1 = LocalDate.ofInstant(date1.toInstant(), java.time.ZoneId.systemDefault());
        LocalDate localDate2 = LocalDate.ofInstant(date2.toInstant(), java.time.ZoneId.systemDefault());
        return ChronoUnit.DAYS.between(localDate1, localDate2);
    }
}
