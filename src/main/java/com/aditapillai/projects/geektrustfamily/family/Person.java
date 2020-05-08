package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.Errors;

import java.util.Objects;
import java.util.Set;

public abstract class Person {
    public final String name;
    public final Man father;
    public final Woman mother;
    private Person spouse;

    protected Person(String name, Person father, Person mother) {
        Objects.requireNonNull(name, Errors.NAME_IS_MANDATORY_ERROR_MESSAGE);
        this.name = name;

        if (father != null && !(father instanceof Man)) {
            throw new RuntimeException(Errors.FATHER_SHOULD_BE_A_MAN_ERROR_MESSAGE);
        }
        if (mother != null && !(mother instanceof Woman)) {
            throw new RuntimeException(Errors.MOTHER_SHOULD_BE_A_WOMAN_ERROR_MESSAGE);
        }

        this.father = (Man) father;
        this.mother = (Woman) mother;
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

    public boolean isMale() {
        return this instanceof Man;
    }

    public boolean isFemale() {
        return this instanceof Woman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((!(o instanceof Person))) return false;
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
