package de.synoa.getting.started.person.out.file.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    @JsonProperty
    private String name;

    @JsonProperty
    private String phone;

    @JsonProperty
    private String company;

    @JsonProperty
    private String pNumber;

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return Objects.equals(birthday, person.birthday) && Objects.equals(name, person.name) && Objects.equals(phone, person.phone)
                && Objects.equals(company, person.company) && Objects.equals(pNumber, person.pNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, name, phone, company, pNumber);
    }
}
