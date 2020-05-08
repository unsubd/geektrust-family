package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.errors.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FamilyTreeTest {

    private Family family;

    @BeforeEach
    public void setup() {
        this.family = Families.createFamily("King Shan", Gender.M);
        this.family.hostWedding("King Shan", "Queen Anga");
    }

    @Test
    public void addChild_MotherChildChildGender_ChildAdded() {
        boolean result = true;
        try {
            this.family.addChild("Queen Anga", "Child", Gender.M);
        } catch (Exception e) {
            result = false;
        }

        assertTrue(result);
    }

    @Test
    public void addChild_IncorrectMotherName_ExceptionThrown() {
        boolean result = false;
        try {
            this.family.addChild("Incorrect Mother Name", "Child", Gender.M);
        } catch (ApiException e) {
            result = e.getMessage()
                      .equals(Errors.PERSON_NOT_FOUND_ERROR_MESSAGE);
        }

        assertTrue(result);
    }

    @Test
    public void addChild_FatherName_ExceptionThrown() {
        boolean result = false;
        try {
            this.family.addChild("King Shan", "Child", Gender.M);
        } catch (ApiException e) {
            result = e.getMessage()
                      .equals(Errors.CHILD_ADDITION_FAILED_ERROR_MESSAGE);
        }

        assertTrue(result);
    }

    @Test
    public void getSons_FatherName_SonsReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);
        Set<String> sons = this.family.getSonsOf("King Shan")
                                      .get();

        assertEquals(Set.of("Prince"), sons);
    }

    @Test
    public void getDaughters_MotherName_DaughtersReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);
        Set<String> sons = this.family.getDaughtersOf("Queen Anga")
                                      .get();

        assertEquals(Set.of("Princess"), sons);
    }

    @Test
    public void getSiblings_ChildName_SiblingsReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);
        this.family.addChild("Queen Anga", "Prince Mundane", Gender.M);

        assertEquals(Set.of("Princess", "Prince Mundane"), this.family.getSiblingsOf("Prince")
                                                                      .get());
    }
}