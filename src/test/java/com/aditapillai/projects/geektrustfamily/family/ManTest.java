package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ManTest {

    @Test
    public void createAMalePerson_GenderAsMale_ManReturned() {
        Person man = Person.builder()
                           .name("TestName")
                           .gender(Gender.M)
                           .build();
        assertTrue(man instanceof Man);
    }

}