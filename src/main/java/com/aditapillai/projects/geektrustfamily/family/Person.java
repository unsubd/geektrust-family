package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

import java.util.Objects;
import java.util.Set;

public abstract class Person {
    public final String name;
    public final Man father;
    public final Woman mother;
    private Person spouse;

    protected Person(String name, Man father, Woman mother) {
        Objects.requireNonNull(name);
        this.name = name;
        this.father = father;
        this.mother = mother;
    }

    public static Person.Builder builder() {
        return new Builder();
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

    public static class Builder {
        private String name;
        private Gender gender;
        private Person father;
        private Person mother;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder father(Person father) {
            this.father = father;
            return this;
        }

        public Builder mother(Person mother) {
            this.mother = mother;
            return this;
        }

        public Person build() {
            Person person;
            if (this.gender == Gender.M) {
                person = new Man(this.name, this.father, this.mother);
            } else {
                person = new Woman(this.name, this.father, this.mother);
            }

            return person;
        }
    }

}
