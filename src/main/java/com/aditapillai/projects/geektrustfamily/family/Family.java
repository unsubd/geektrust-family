package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

import java.util.Optional;
import java.util.Set;

public interface Family {
    void addChild(String motherName, String childName, Gender childGender);

    Optional<? extends Set<String>> getSonsOf(String name);

    Optional<? extends Set<String>> getDaughtersOf(String name);

    Optional<? extends Set<String>> getSiblingsOf(String name);

    Optional<String> getMotherOf(String name);

    Optional<String> getFatherOf(String name);

    Optional<? extends Set<String>> getPaternalUnclesOf(String name);

    Optional<? extends Set<String>> getMaternalUnclesOf(String name);

    Optional<? extends Set<String>> getPaternalAuntsOf(String name);

    Optional<? extends Set<String>> getMaternalAuntsOf(String name);

    Optional<? extends Set<String>> getSistersInLawOf(String name);

    Optional<? extends Set<String>> getBrothersInLawOf(String name);


    void hostWedding(String husbandName, String wifeName);

    boolean contains(String name);
}
