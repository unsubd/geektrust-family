package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WomanTest {

    @Test
    public void createAFemalePerson_GenderAsFemale_WomanReturned() {
        Person man = Person.builder()
                           .name("TestName")
                           .gender(Gender.F)
                           .build();
        assertTrue(man instanceof Woman);
    }

    @Test
    public void addChild_AddChildToWoman_ChildAdded() {
        Woman woman = new Woman("Test Name", null, null);
        woman.addChild(Person.builder()
                             .gender(Gender.M)
                             .name("New Child")
                             .build());
        assertTrue(woman.getChildren()
                        .stream()
                        .map(person -> person.name)
                        .anyMatch("New Child"::equals));
    }

}