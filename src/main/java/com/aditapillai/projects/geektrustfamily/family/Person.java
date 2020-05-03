package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

import java.util.Objects;
import java.util.Set;

public abstract class Person {
    public final String name;
    private Person spouse;

    protected Person(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    void marry(Person spouse) {
        this.spouse = spouse;
        if (spouse.getSpouse() != this) {
            spouse.marry(this);
        }
    }

    public abstract Set<Person> getChildren();

    public Person getSpouse() {
        return spouse;
    }

    public static Person.Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String name;
        private Gender gender;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Person build() {
            if (this.gender == Gender.M) {
                return new Man(this.name);
            } else {
                return new Woman(this.name);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.name, this instanceof Woman ? "F" : "M");
    }

}
