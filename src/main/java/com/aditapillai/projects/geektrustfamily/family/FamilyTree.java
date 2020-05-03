package com.aditapillai.projects.geektrustfamily.family;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FamilyTree implements Family {

    private Person root;
    private final Map<String, Person> memberDirectory = new HashMap<>();

    FamilyTree(Person origin) {
        Objects.requireNonNull(origin);
        this.root = origin;
        this.memberDirectory.put(origin.name, origin);
    }

    @Override
    public void addChild(String motherName, String childName, Gender childGender) {
        if (this.memberDirectory.containsKey(motherName)) {
            Person person = this.memberDirectory.get(motherName);
            if (person instanceof Woman) {
                Woman mother = (Woman) person;
                Person child;

                if (childGender == Gender.F) {
                    child = new Woman(childName);
                } else {
                    child = new Man(childName);
                }
                mother.addChild(child);
                this.memberDirectory.put(childName, child);
            }
        }
    }

    @Override
    public String getRelative(String name, String relationship) {
        return null;
    }

    @Override
    public void hostWedding(String husbandName, String wifeName) {
        Person husband = this.memberDirectory.getOrDefault(husbandName, new Man(husbandName));
        Person wife = this.memberDirectory.getOrDefault(wifeName, new Woman(wifeName));
        husband.marry(wife);

        this.memberDirectory.put(husbandName, husband);
        this.memberDirectory.put(wifeName, wife);

    }

    @Override
    public boolean contains(String name) {
        return false;
    }

}
