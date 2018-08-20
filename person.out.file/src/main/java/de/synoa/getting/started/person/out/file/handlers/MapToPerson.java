package de.synoa.getting.started.person.out.file.handlers;

import de.synoa.getting.started.person.out.file.model.Person;
import org.apache.camel.Body;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;

public class MapToPerson {

    public Person handle(@Body Document personDocument) {

        if (personDocument == null) {
            return null;
        }

        Person person = new Person();

        final String name = String.format("%s, %s", personDocument.get("lastName"), personDocument.get("firstName"));
        final LocalDate birthday = personDocument.getDate("birthday").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        person.setBirthday(birthday);
        person.setName(name);
        person.setPhone(personDocument.getString("phone"));
        person.setCompany(personDocument.getString("company"));
        person.setpNumber(personDocument.getString("personalNumber"));

        return person;
    }
}
