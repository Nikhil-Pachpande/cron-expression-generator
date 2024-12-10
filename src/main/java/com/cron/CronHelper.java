package com.cron;

public class CronHelper {

    /**
     * This method validates whether a given string is a valid cron expression.
     * @param expression The cron expression to validate.
     * @return true if the expression is valid, false otherwise.
     */
    public static boolean isValidCronExpression(String expression) {
        // validation for cron format: minute, hour, day, month, weekday
        String cronPattern = "^(\\*|([0-5]?[0-9])) (\\*|([01]?[0-9]|2[0-3])) (\\*|[1-9]|[12][0-9]|3[01]) (\\*|(1[0-2]|[1-9])) (\\*|([0-6]))$";
        return expression.matches(cronPattern);
    }
}
