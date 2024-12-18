package io.github;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CronGeneratorTest {

    @Test
    void testEveryMinute() {
        String result = CronGenerator.generateCronExpression("every minute");
        assertEquals("*/1 * * * *", result);
    }

    @Test
    void testEveryHour() {
        String result = CronGenerator.generateCronExpression("every hour");
        assertEquals("0 * * * *", result);
    }

    @Test
    void testEveryMondayAt() {
        String result = CronGenerator.generateCronExpression("every Monday at 8AM");
        assertEquals("0 8 * * 1", result);
    }

    @Test
    void testWeekdays() {
        String result = CronGenerator.generateCronExpression("weekdays at 9 AM");
        assertEquals("0 9 * * 1-5", result);
    }

    @Test
    void testStartOfMonth() {
        String result = CronGenerator.generateCronExpression("start of the month");
        assertEquals("0 0 1 * *", result);
    }

    @Test
    void testMidOfMonth() {
        String result = CronGenerator.generateCronExpression("mid of the month");
        assertEquals("0 0 15 * *", result);
    }

    @Test
    void testEndOfMonth() {
        String result = CronGenerator.generateCronExpression("end of the month");
        assertEquals("59 23 28-31 * *", result);
    }

    @Test
    void testSpecificDayOfMonth() {
        String result = CronGenerator.generateCronExpression("5th day of the month at 8AM");
        assertEquals("0 8 5 * *", result);
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            CronGenerator.generateCronExpression("invalid cron text");
        });
    }
}
