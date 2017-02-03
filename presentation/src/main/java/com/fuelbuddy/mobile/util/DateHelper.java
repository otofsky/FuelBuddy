package com.fuelbuddy.mobile.util;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by zjuroszek on 25.11.16.
 */

public class DateHelper {

    private static final int UNKOWN_HOURS = 0;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static DateTime convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT);
        DateTime date = formatter.parseDateTime(dateString);
        return date;
    }


    public static int isOlderThanData(String fuelLastUpDateString) {
        if (!StringHelper.isNullOrEmpty(fuelLastUpDateString)) {
            DateTime dt = convertStringToDate(fuelLastUpDateString);
            DateTime dateTime = new DateTime();
            Duration p = new Duration(dt, dateTime);
            int hours = (int) p.getStandardHours();
            return hours;
        }
        return UNKOWN_HOURS;
    }
}
