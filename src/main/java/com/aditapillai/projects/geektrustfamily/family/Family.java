package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

import java.util.Optional;
import java.util.Set;

public interface Family {
    void addChild(String motherName, String childName, Gender childGender);

    Optional<Set<String>> getSonsOf(String name);

    Optional<Set<String>> getDaughtersOf(String name);

    Optional<Set<String>> getSiblingsOf(String name);

    Optional<String> getMotherOf(String name);

    Optional<String> getFatherOf(String name);

    Optional<Set<String>> getPaternalUnclesOf(String name);

    Optional<Set<String>> getMaternalUnclesOf(String name);

    Optional<Set<String>> getPaternalAuntsOf(String name);

    Optional<Set<String>> getMaternalAuntsOf(String name);

    Optional<Set<String>> getSistersInLawOf(String name);


    void hostWedding(String husbandName, String wifeName);

    boolean contains(String name);
}
