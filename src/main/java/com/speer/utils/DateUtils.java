package com.speer.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils {
    public static LocalDateTime getCurrentTime(){
        ZoneId oldZone = ZoneId.of(ZoneId.systemDefault().getId());
        //Canada/Mountain
        //Africa/Lagos
        ZoneId newZone = ZoneId.of("Africa/Lagos");
        LocalDateTime oldDateTime = LocalDateTime.now();
        return oldDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
    }

    public static LocalDate toDate(String date){
        List<String> patterns = Arrays.asList("yyyy-MM-dd", "dd-MMM-yyyy"
                , "yyyy-MM-dd'T'HH:mm:ssXXX");
        for(String pattern: patterns){
            try{
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .toFormatter(Locale.ENGLISH);
                return LocalDate.parse(date, formatter);
            }
            catch(Exception e){}
        }
        return null;
    }

    public static Date toUtilDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date toUtilDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toDateTime(String date){
        List<String> patterns = Arrays.asList("yyyy-MM-dd HH:mm:ss");
        for(String pattern: patterns){
            try{
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .toFormatter(Locale.ENGLISH);
                return LocalDateTime.parse(date, formatter);
            }
            catch(Exception e){}
        }
        return null;
    }

    public static Long getWorkingDaysBetween(LocalDate startDate, LocalDate endDate){
        Long calendarDays = ChronoUnit.DAYS.between(startDate, endDate);
        Long result = 5L * (calendarDays / 7L);
        Long r = calendarDays % 7L;
        Integer start = startDate.getDayOfWeek().getValue();
        Integer remainder = 0;
        for(int i = 0; i < r; i++){
            if(start + i < 6) remainder++;
        }
        Long total = result + remainder;
        return total;
    }

    public static Long getBusinessMinutesBetween(LocalDateTime startTime, LocalDateTime endTime){
        LocalDateTime firstEOD = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIDNIGHT)
                .plusHours(16L)
                .plusMinutes(30L);
        LocalDateTime endSOD = LocalDateTime.of(endTime.toLocalDate(), LocalTime.MIDNIGHT)
                .plusHours(8L);
        if(startTime.toLocalDate().isEqual(endTime.toLocalDate())){
            if(startTime.isAfter(firstEOD) || endTime.isBefore(endSOD)) return 0L;
            LocalDateTime adjustedStart = startTime.isBefore(endSOD) ? endSOD: startTime;
            LocalDateTime adjustedEnd = endTime.isAfter(firstEOD) ? firstEOD: endTime;
            return ChronoUnit.MINUTES.between(adjustedStart, adjustedEnd);
        }
        Long firstDayMinutes = startTime.isAfter(firstEOD)
                || startTime.getDayOfWeek() == DayOfWeek.SATURDAY
                || startTime.getDayOfWeek() == DayOfWeek.SUNDAY ? 0L: Math.min(510L, ChronoUnit.MINUTES.between(startTime, firstEOD));
        Long lastDayMinutes = endTime.isBefore(endSOD)
                || endTime.getDayOfWeek() == DayOfWeek.SATURDAY
                || endTime.getDayOfWeek() == DayOfWeek.SUNDAY ? 0L: Math.min(510L, ChronoUnit.MINUTES.between(endSOD, endTime));
        Long intervalDaysMinutes = getWorkingDaysBetween(startTime.toLocalDate().plusDays(1L), endTime.toLocalDate().minusDays(1L)) * 510L;
        return Math.max(0L, intervalDaysMinutes) + firstDayMinutes + lastDayMinutes;
    }
}

