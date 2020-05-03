package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        if (!this.contains(motherName)) {
            throw new RuntimeException(String.format("Person with name %s not found", motherName));
        }
        Optional.of(this.memberDirectory.get(motherName))
                .filter(person -> person instanceof Woman)
                .map(person -> (Woman) person)
                .map(mother -> addChildToMother(mother, childName, childGender))
                .orElseThrow(() -> new RuntimeException(String.format("Person with name %s not a mother", motherName)));
    }

    private Woman addChildToMother(Woman mother, String childName, Gender childGender) {
        Person child = Person.builder()
                             .name(childName)
                             .gender(childGender)
                             .build();
        mother.addChild(child);
        this.memberDirectory.put(childName, child);
        return mother;
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
        return this.memberDirectory.containsKey(name);
    }

}
