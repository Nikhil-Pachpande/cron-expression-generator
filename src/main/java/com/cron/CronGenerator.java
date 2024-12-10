package com.cron;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CronGenerator {

    public static String generateCronExpression(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        text = text.toLowerCase().trim();

        if (text.contains("every minute")) {
            return "*/1 * * * *";
        } else if (text.contains("every hour")) {
            return "0 * * * *";
        }

        if (text.contains("every day at")) {
            return handleSpecificTimeOfDay(text, "*");
        }
        else if (text.contains("every")) {
            return handleSpecificDayAtTime(text);
        }

        if (text.contains("weekdays")) {
            return "0 9 * * 1-5";
        } else if (text.contains("weekend")) {
            return "0 17 * * 6,7";
        }

        if (text.contains("start of the month")) {
            return "0 0 1 * *";
        } else if (text.contains("mid of the month")) {
            return "0 0 15 * *";
        } else if (text.contains("end of the month")) {
            return "59 23 28-31 * *";
        }

        if (text.contains("th day of the month")) {
            return handleSpecificDayOfMonth(text);
        }

        throw new IllegalArgumentException("Cannot parse the given text into a cron expression.");
    }

    private static String handleSpecificDayAtTime(String text) {
        Pattern pattern = Pattern.compile("every (monday|tuesday|wednesday|thursday|friday|saturday|sunday) at (\\d+)(\\s?)(am|pm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String day = matcher.group(1).toLowerCase();
            String hour = matcher.group(2);
            String period = matcher.group(3).toLowerCase();

            int dayOfWeek = mapDayToCron(day);

            if ("pm".equals(period) && Integer.parseInt(hour) < 12) {
                hour = String.valueOf(Integer.parseInt(hour) + 12);
            } else if ("am".equals(period) && Integer.parseInt(hour) == 12) {
                hour = "0";
            }

            return "0 " + hour + " * * " + dayOfWeek;
        }

        throw new IllegalArgumentException("Unable to parse day and time in text.");
    }

    private static int mapDayToCron(String day) {
        switch (day) {
            case "sunday": return 0;
            case "monday": return 1;
            case "tuesday": return 2;
            case "wednesday": return 3;
            case "thursday": return 4;
            case "friday": return 5;
            case "saturday": return 6;
            default: throw new IllegalArgumentException("Invalid day of the week");
        }
    }

    private static String handleSpecificDayOfMonth(String text) {
        Pattern pattern = Pattern.compile("(\\d+)(st|nd|rd|th) day of the month at (\\d+)(\\s?)(am|pm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String day = matcher.group(1);
            String hour = matcher.group(3);
            String period = matcher.group(4).toLowerCase();

            if ("pm".equals(period) && Integer.parseInt(hour) < 12) {
                hour = String.valueOf(Integer.parseInt(hour) + 12);
            } else if ("am".equals(period) && Integer.parseInt(hour) == 12) {
                hour = "0";
            }

            return "0 " + hour + " " + day + " * *";
        }

        throw new IllegalArgumentException("Unable to parse specific day of the month and time.");
    }

    private static String handleSpecificTimeOfDay(String text, String day) {
        Pattern pattern = Pattern.compile("every day at (\\d+)(\\s?)(am|pm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String hour = matcher.group(1);
            String period = matcher.group(2).toLowerCase();

            if ("pm".equals(period) && Integer.parseInt(hour) < 12) {
                hour = String.valueOf(Integer.parseInt(hour) + 12);
            } else if ("am".equals(period) && Integer.parseInt(hour) == 12) {
                hour = "0";
            }

            return "0 " + hour + " * * *";
        }

        throw new IllegalArgumentException("Unable to parse time in text.");
    }
}
