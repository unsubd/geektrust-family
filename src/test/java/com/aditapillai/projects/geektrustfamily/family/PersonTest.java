package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.Errors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class PersonTest {

    @Test
    public void createPerson_GenderName_PersonReturned() {
        Woman woman = (Woman) Person.builder()
                                    .gender(Gender.F)
                                    .name("Test Person")
                                    .build();
        Assertions.assertEquals("Test Person", woman.name);
    }

    @Test
    public void createPerson_Gender_ExceptionThrown() {
        boolean result = false;
        try {
            Woman woman = (Woman) Person.builder()
                                        .gender(Gender.F)
                                        .build();
        } catch (NullPointerException nullPointerException) {
            result = nullPointerException.getMessage()
                                         .equals(Errors.NAME_IS_MANDATORY_ERROR_MESSAGE);
        }

        Assertions.assertTrue(result);

    }

    @Test
    public void createPerson_Name_DefaultGenderWomanReturned() {
        Person person = Person.builder()
                              .name("Name")
                              .build();


        Assertions.assertTrue(person instanceof Woman);
    }

    @Test
    public void createPerson_NameGenderMother_ValidPersonWithMotherReturned() {
        Woman woman = (Woman) Person.builder()
                                    .name("Name")
                                    .gender(Gender.F)
                                    .mother(Person.builder()
                                                  .name("Queen")
                                                  .gender(Gender.F)
                                                  .build())
                                    .build();


        Assertions.assertNotNull(woman.mother);
    }

    @Test
    public void createPerson_NameGenderFather_ValidPersonWithFatherReturned() {
        Woman woman = (Woman) Person.builder()
                                    .name("Name")
                                    .gender(Gender.F)
                                    .father(Person.builder()
                                                  .name("King")
                                                  .gender(Gender.M)
                                                  .build())
                                    .build();

        Assertions.assertNotNull(woman.father);
    }

    @Test
    public void createPerson_NameGenderIncorrectFatherGender_ExceptionThrown() {
        boolean result = false;
        try {
            Woman woman = (Woman) Person.builder()
                                        .name("Name")
                                        .gender(Gender.F)
                                        .father(Person.builder()
                                                      .name("King")
                                                      .gender(Gender.F)
                                                      .build())
                                        .build();
        } catch (RuntimeException exception) {
            result = exception.getMessage()
                              .equals(Errors.FATHER_SHOULD_BE_A_MAN_ERROR_MESSAGE);
        }

        Assertions.assertTrue(result);
    }

    @Test
    public void createPerson_NameGenderIncorrectMotherGender_ExceptionThrown() {
        boolean result = false;
        try {
            Woman woman = (Woman) Person.builder()
                                        .name("Name")
                                        .gender(Gender.F)
                                        .mother(Person.builder()
                                                      .name("Queen")
                                                      .gender(Gender.M)
                                                      .build())
                                        .build();
        } catch (RuntimeException exception) {
            result = exception.getMessage()
                              .equals(Errors.MOTHER_SHOULD_BE_A_WOMAN_ERROR_MESSAGE);
        }

        Assertions.assertTrue(result);
    }

    @Test
    public void marry_ValidPerson_SpouseAdded() {
        Person wife = Person.builder()
                            .name("Queen")
                            .gender(Gender.F)
                            .build();
        Person husband = Person.builder()
                               .name("King")
                               .gender(Gender.M)
                               .build();

        husband.marry(wife);
        Assertions.assertTrue(wife.getSpouse() == husband && husband.getSpouse() == wife);
    }

    @Test
    public void isMale_MalePerson_TrueReturned() {
        Person person = Person.builder()
                              .name("King")
                              .gender(Gender.M)
                              .build();
        Assertions.assertTrue(person.isMale());
    }

    @Test
    public void isFemale_FemalePerson_TrueReturned() {
        Person person = Person.builder()
                              .name("Queen")
                              .gender(Gender.F)
                              .build();
        Assertions.assertTrue(person.isFemale());
    }

    @Test
    public void equals_PersonsWithSameName_TrueReturned() {
        Person person = Person.builder()
                              .name("SameName")
                              .gender(Gender.F)
                              .build();

        Person person2 = Person.builder()
                               .name("SameName")
                               .gender(Gender.M)
                               .build();

        Assertions.assertTrue(person.equals(person2));
    }

    @Test
    public void hashcode_Person_ObjectHashReturned() {
        Person person = Person.builder()
                              .name("SameName")
                              .gender(Gender.F)
                              .build();

        Assertions.assertEquals(Objects.hash("SameName"), person.hashCode());
    }

}