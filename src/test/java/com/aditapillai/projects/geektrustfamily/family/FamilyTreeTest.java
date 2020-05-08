package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.errors.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getDaughters_FatherNameWithNoSons_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Princess", Gender.F);

        assertTrue(this.family.getSonsOf("King Shan")
                              .isEmpty());
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
    public void getDaughters_MotherNameWithNoDaughters_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);

        assertTrue(this.family.getDaughtersOf("Queen Anga")
                              .isEmpty());
    }

    @Test
    public void getSiblings_ChildName_SiblingsReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);
        this.family.addChild("Queen Anga", "Prince Mundane", Gender.M);

        assertEquals(Set.of("Princess", "Prince Mundane"), this.family.getSiblingsOf("Prince")
                                                                      .get());
    }

    @Test
    public void getSiblings_ChildNameWithNoSiblings_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);

        assertTrue(this.family.getSiblingsOf("Prince")
                              .isEmpty());
    }

    @Test
    public void getPaternalUncles_PersonNameWhoHasPaternalUncles_PaternalUnclesReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Prince Mundane", Gender.M);

        this.family.hostWedding("Prince Mundane", "Princess Galaxy");
        this.family.addChild("Princess Galaxy", "Child", Gender.F);

        assertEquals(Set.of("Prince"), this.family.getPaternalUnclesOf("Child")
                                                  .get());
    }

    @Test
    public void getPaternalUncles_PersonNameWhoDoesNotHavePaternalUncles_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Prince Mundane", Gender.M);

        this.family.hostWedding("Prince Mundane", "Princess Galaxy");
        this.family.addChild("Princess Galaxy", "Child", Gender.F);

        assertTrue(this.family.getPaternalUnclesOf("Child")
                              .isEmpty());
    }

    @Test
    public void getMaternalUncles_PersonNameWhoHasMaternalUncles_MaternalUnclesReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);

        this.family.hostWedding("Prince Mundane", "Princess");
        this.family.addChild("Princess", "Child", Gender.F);

        assertEquals(Set.of("Prince"), this.family.getMaternalUnclesOf("Child")
                                                  .get());
    }

    @Test
    public void getMaternalUncles_PersonNameWhoDoesNotHaveMaternalUncles_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Princess", Gender.F);

        this.family.hostWedding("Prince Mundane", "Princess");
        this.family.addChild("Princess", "Child", Gender.F);

        assertTrue(this.family.getMaternalUnclesOf("Child")
                              .isEmpty());
    }

    @Test
    public void getPaternalAunts_PersonNameWithPaternalAunts_PaternalAuntsReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);

        this.family.hostWedding("Prince", "Princess Galaxy");
        this.family.addChild("Princess Galaxy", "Child", Gender.F);

        assertEquals(Set.of("Princess"), this.family.getPaternalAuntsOf("Child")
                                                    .get());
    }

    @Test
    public void getPaternalAunts_PersonNameWhoDoesNotHavePaternalAunts_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);

        this.family.hostWedding("Prince", "Princess Galaxy");
        this.family.addChild("Princess Galaxy", "Child", Gender.F);

        assertTrue(this.family.getPaternalAuntsOf("Child")
                              .isEmpty());
    }

    @Test
    public void getMaternalAunts_PersonNameWithMaternalAunts_MaternalAuntsReturned() {
        this.family.addChild("Queen Anga", "Prince Sunshine", Gender.F);
        this.family.addChild("Queen Anga", "Princess", Gender.F);

        this.family.hostWedding("Prince Moon", "Princess");
        this.family.addChild("Princess", "Child", Gender.F);

        assertEquals(Set.of("Prince Sunshine"), this.family.getMaternalAuntsOf("Child")
                                                           .get());
    }

    @Test
    public void getMaternalAunts_PersonNameWhoDoesNotHaveMaternalAunts_EmptyOptionalReturned() {
        this.family.addChild("Queen Anga", "Princess", Gender.F);

        this.family.hostWedding("Prince Moon", "Princess");
        this.family.addChild("Princess", "Child", Gender.F);

        assertTrue(this.family.getPaternalAuntsOf("Child")
                              .isEmpty());
    }

    @Test
    public void getSistersInLaw_ManWithSistersInLaw_SistersInLawReturned() {
        this.family.addChild("Queen Anga", "Princess Sunshine", Gender.F);
        this.family.addChild("Queen Anga", "Princess", Gender.F);
        this.family.hostWedding("Prince Moon", "Princess");

        assertEquals(Set.of("Princess Sunshine"), this.family.getSistersInLawOf("Prince Moon")
                                                             .get());
    }

    @Test
    public void getSistersInLaw_WomanWithSistersInLaw_SistersInLawReturned() {
        this.family.addChild("Queen Anga", "Princess Sunshine", Gender.F);
        this.family.addChild("Queen Anga", "Princess Stardust", Gender.F);
        this.family.addChild("Queen Anga", "Prince Moon", Gender.M);
        this.family.hostWedding("Prince Moon", "Princess");

        assertEquals(Set.of("Princess Sunshine", "Princess Stardust"), this.family.getSistersInLawOf("Princess")
                                                                                  .get());
    }

    @Test
    public void getBrothersInLaw_ManWithBrothersInLaw_BrothersInLawReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess", Gender.F);
        this.family.hostWedding("Prince Moon", "Princess");

        assertEquals(Set.of("Prince"), this.family.getBrothersInLawOf("Prince Moon")
                                                  .get());
    }

    @Test
    public void getBrothersInLaw_WomanWithBrothersInLaw_BrothersInLawReturned() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.addChild("Queen Anga", "Princess Stardust", Gender.F);
        this.family.addChild("Queen Anga", "Prince Moon", Gender.M);
        this.family.hostWedding("Prince Moon", "Princess");

        assertEquals(Set.of("Prince"), this.family.getBrothersInLawOf("Princess")
                                                  .get());
    }

    @Test
    public void hostWedding_ExistingPeople_Success() {
        this.family.addChild("Queen Anga", "Prince", Gender.M);
        this.family.hostWedding("Prince", "Princess Sunshine");

        assertEquals("Prince", this.family.getPerson("Princess Sunshine")
                                          .get()
                                          .getSpouse().name);
    }

    @Test
    public void hostWedding_PeopleNotPartOfFamily_ExceptionThrown() {
        boolean result = true;
        try {
            this.family.hostWedding("Prince", "Princess Sunshine");
        } catch (ApiException exception) {
            result = exception.getMessage()
                              .equals(Errors.ONE_MEMBER_SHOULD_BE_FAMILY_ERROR_MESSAGE);
        }
        assertTrue(result);
    }

    @Test
    public void contains_MemberOfFamily_TrueReturned() {
        assertTrue(this.family.contains("King Shan"));
    }

    @Test
    public void contains_NonMembersOfFamily_FalseReturned() {
        assertFalse(this.family.contains("Supreme King Sun"));
    }
}