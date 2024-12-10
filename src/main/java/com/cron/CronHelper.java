package com.cron;

public class CronHelper {

    public static boolean isValidCronExpression(String expression) {
        String cronPattern = "^(\\*|([0-5]?[0-9])) (\\*|([01]?[0-9]|2[0-3])) (\\*|[1-9]|[12][0-9]|3[01]) (\\*|(1[0-2]|[1-9])) (\\*|([0-6]))$";
        return expression.matches(cronPattern);
    }
}
