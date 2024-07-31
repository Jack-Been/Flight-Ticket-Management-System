package com.company.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTime implements Serializable {
    private final LocalDateTime dateTime;

    public DateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy, h:mm a", Locale.ENGLISH));
    }
}
