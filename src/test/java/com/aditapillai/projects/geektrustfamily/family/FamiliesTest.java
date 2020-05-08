package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FamiliesTest {

    @Test
    void createFamily_NameAndGenderOfOrigin_FamilyReturned() {
        Family family = Families.createFamily("King Shan", Gender.M);
        assertTrue(family.contains("King Shan"));
    }
}