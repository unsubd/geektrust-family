package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

public interface Family {
    void addChild(String motherName, String childName, Gender childGender);

    String getRelative(String name, String relationship);

    void hostWedding(String husbandName, String wifeName);

    boolean contains(String name);
}
