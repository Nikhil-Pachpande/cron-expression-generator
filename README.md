# cron-expression-generator
![Maven Central](https://img.shields.io/maven-central/v/io.github.nikhil-pachpande/cron-expression-generator?color=blue)

A Java utility library that converts human-readable text into cron expressions. This library handles a wide range of time formats, including time intervals, specific days of the week, specific days of the month, and special phrases like "start of the month" and "end of the month."

This library is published to maven central repository (sonatype) and the package details can be found at ```https://central.sonatype.com/artifact/io.github.nikhil-pachpande/cron-expression-generator```.

# Features
- Converts phrases like "every minute" to the cron expression */1 * * * *.
- Converts "every Monday at 8 AM" to 0 8 * * 1.
- Supports weekdays and weekends like "weekdays at 9 AM" (0 9 * * 1-5).
- Handles "start of the month", "mid of the month", and "end of the month".
- Parses specific days of the month such as "5th day of the month at 8 AM" (0 8 5 * *).
- Case insensitive, with handling for spaces around "AM/PM".

# Installation & Usage
- To use the library in your project, add the following dependency in the pom.xml file of your project:
```
<dependency>
  <groupId>com.example</groupId>
  <artifactId>cron-expression-generator</artifactId>
  <version>1.0.0</version>
</dependency>
```
- to build locally, run ```mvn clean install```
