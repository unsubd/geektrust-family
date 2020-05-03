package com.aditapillai.projects.geektrustfamily.family;

import java.util.Objects;

abstract class Person {
    final String name;
    private Person spouse;

    protected Person(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    Person getSpouse() {
        return spouse;
    }

    void marry(Person spouse) {
        this.spouse = spouse;
        if (spouse.getSpouse() != this) {
            spouse.marry(this);
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
}
