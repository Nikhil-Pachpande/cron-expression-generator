package com.cron;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CronGenerator {

    /**
     * This method generates a cron expression based on simple text input.
     * The text is parsed to generate the corresponding cron expression.
     * @param text User input text to convert into a cron expression.
     * @return Corresponding cron expression in standard cron format.
     * @throws IllegalArgumentException if the text cannot be parsed into a valid cron expression.
     */
    public static String generateCronExpression(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        text = text.toLowerCase().trim();

        // for general cases: any minute, any hour, for any day
        if (text.contains("every minute")) {
            return "*/1 * * * *";
        } else if (text.contains("every hour")) {
            return "0 * * * *";
        }

        // to handle specific time phrase like: "every day at [time]"
        if (text.contains("every day at")) {
            return handleSpecificTimeOfDay(text, "*");
        }
        // to handle weekday phrases like: "every monday at"
        else if (text.contains("every")) {
            return handleSpecificDayAtTime(text);
        }

        // to handle phrases like: "weekdays" or "weekend"
        if (text.contains("weekdays")) {
            return "0 9 * * 1-5"; // Weekdays at 9 AM
        } else if (text.contains("weekend")) {
            return "0 17 * * 6,7"; // Weekends at 5 PM
        }

        // to handle phrases like: "start", "mid", and "end of the month"
        if (text.contains("start of the month")) {
            return "0 0 1 * *";
        } else if (text.contains("mid of the month")) {
            return "0 0 15 * *";
        } else if (text.contains("end of the month")) {
            return "59 23 28-31 * *";
        }

        // to handle specific days of the month (nth day of the month)
        if (text.contains("th day of the month")) {
            return handleSpecificDayOfMonth(text);
        }

        // if the user input cannot be parsed
        throw new IllegalArgumentException("Cannot parse the given text into a cron expression.");
    }

    /**
     * Handles specific days of the week like "every Monday at 8 AM".
     * This method parses the input to extract the day and time, then returns the corresponding cron expression.
     * @param text User input specifying day and time (e.g., "every Monday at 8 AM").
     * @return Corresponding cron expression.
     */
    private static String handleSpecificDayAtTime(String text) {
        // to handle phrases like: "every Monday at 8 AM"
        Pattern pattern = Pattern.compile("every (monday|tuesday|wednesday|thursday|friday|saturday|sunday) at (\\d+)(\\s?)(am|pm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String day = matcher.group(1).toLowerCase();
            String hour = matcher.group(2);
            String period = matcher.group(4).toLowerCase();

            // to map days to cron values (0-6)
            int dayOfWeek = mapDayToCron(day);

            // to convert the time to 24-hour format
            if ("pm".equals(period) && Integer.parseInt(hour) < 12) {
                hour = String.valueOf(Integer.parseInt(hour) + 12); // to convert PM to 24-hour format
            } else if ("am".equals(period) && Integer.parseInt(hour) == 12) {
                hour = "0"; // for midnight
            }

            return "0 " + hour + " * * " + dayOfWeek;
        }

        throw new IllegalArgumentException("Unable to parse day and time in text.");
    }

    /**
     * This method maps day of the week (Sunday, Monday, etc.) to the corresponding cron value (0-6).
     * @param day The name of the day (e.g., "Monday").
     * @return The corresponding cron value for the day of the week (0 for Sunday, 1 for Monday, etc.).
     */
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

    /**
     * This method handles specific day of the month like "5th day of the month at 8 AM".
     * Also, parses the input to extract the day and time, then returns the corresponding cron expression.
     * @param text User input specifying the day of the month and time (e.g., "5th day of the month at 8 AM").
     * @return Corresponding cron expression.
     */
    private static String handleSpecificDayOfMonth(String text) {
        // to handle phrases like: "5th day of the month at 8 AM"
        Pattern pattern = Pattern.compile("(\\d+)(st|nd|rd|th) day of the month at (\\d+)(\\s?)(am|pm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String day = matcher.group(1);
            String hour = matcher.group(3);
            String period = matcher.group(5).toLowerCase();

            // to convert the time to 24-hour format
            if ("pm".equals(period) && Integer.parseInt(hour) < 12) {
                hour = String.valueOf(Integer.parseInt(hour) + 12); // to convert PM to 24-hour format
            } else if ("am".equals(period) && Integer.parseInt(hour) == 12) {
                hour = "0"; // for midnight
            }

            return "0 " + hour + " " + day + " * *"; // for cron expression format: minute hour day * *
        }

        throw new IllegalArgumentException("Unable to parse specific day of the month and time.");
    }

    /**
     * This method handles specific time of day for "every day at [time]" scenario.
     * Also, parses the input to extract the time and returns the corresponding cron expression.
     * @param text User input specifying the time of day (e.g., "every day at 12 PM").
     * @param day Cron day field (can be "*" for every day).
     * @return Corresponding cron expression.
     */
    private static String handleSpecificTimeOfDay(String text, String day) {
        // to handle phrases like: "every day at 12 PM"
        Pattern pattern = Pattern.compile("every day at (\\d+)(\\s?)(am|pm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String hour = matcher.group(1);
            String period = matcher.group(3).toLowerCase();

            // to convert the time to 24-hour format
            if ("pm".equals(period) && Integer.parseInt(hour) < 12) {
                hour = String.valueOf(Integer.parseInt(hour) + 12); // to convert PM to 24-hour format
            } else if ("am".equals(period) && Integer.parseInt(hour) == 12) {
                hour = "0"; // for midnight
            }

            return "0 " + hour + " * * *"; // for cron expression format: minute hour * * *
        }

        throw new IllegalArgumentException("Unable to parse time in text.");
    }
}
