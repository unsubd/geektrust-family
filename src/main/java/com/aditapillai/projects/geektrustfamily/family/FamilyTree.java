package com.aditapillai.projects.geektrustfamily.family;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FamilyTree implements Family {

    private Person root;
    private final Map<String, Person> memberDirectory = new HashMap<>();

    public FamilyTree(Person origin) {
        Objects.requireNonNull(origin);
        this.root = origin;
        this.memberDirectory.put(origin.name, origin);
    }

    @Override
    public void addChild(String motherName, String childName) {

    }

    @Override
    public String getRelative(String name, String relationship) {
        return null;
    }

    @Override
    public void hostWedding(String husbandName, String wifeName) {

    }

    @Override
    public boolean contains(String name) {
        return false;
    }

}
