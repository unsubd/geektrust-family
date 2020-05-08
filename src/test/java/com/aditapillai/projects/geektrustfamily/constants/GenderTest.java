package com.aditapillai.projects.geektrustfamily.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    void parse_Male_MaleGenderReturned() {
        Assertions.assertEquals(Gender.M, Gender.parse("Male"));
    }

    @Test
    void parse_Female_FemaleGenderReturned() {
        Assertions.assertEquals(Gender.F, Gender.parse("Female"));
    }
}