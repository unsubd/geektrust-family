package com.aditapillai.projects.geektrustfamily.family;

public interface Family {
    void addChild(String motherName, String childName);

    String getRelative(String name, String relationship);

    void hostWedding(String husbandName, String wifeName);

    boolean contains(String name);
}
