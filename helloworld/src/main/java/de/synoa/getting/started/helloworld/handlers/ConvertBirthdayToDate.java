package de.synoa.getting.started.helloworld.handlers;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConvertBirthdayToDate {

    private static final String BIRTHDAY = "birthday";

    private static final ThreadLocal<DateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    @Handler
    public Document handle(@Body Document person) throws ParseException {

        final String birthday = person.getString(BIRTHDAY);

        if (birthday != null) {
            person.put(BIRTHDAY, dateFormat.get().parse(birthday));
        } else {
            person.put(BIRTHDAY, null);
        }

        return person;
    }
}
