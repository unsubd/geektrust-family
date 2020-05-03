package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.Errors;

import java.util.*;

class FamilyTree implements Family {

    private final Map<String, Person> memberDirectory = new HashMap<>();
    private final Person root;

    FamilyTree(Person origin) {
        Objects.requireNonNull(origin);
        this.root = origin;
        this.memberDirectory.put(origin.name, origin);
    }

    @Override
    public void addChild(String motherName, String childName, Gender childGender) {
        if (!this.contains(motherName)) {
            throw new RuntimeException(Errors.PERSON_NOT_FOUND_ERROR_MESSAGE);
        }
        Optional.of(this.memberDirectory.get(motherName))
                .filter(person -> person instanceof Woman)
                .map(person -> (Woman) person)
                .map(mother -> addChildToMother(mother, childName, childGender))
                .orElseThrow(() -> new RuntimeException(Errors.CHILD_ADDITION_FAILED_ERROR_MESSAGE));
    }

    private Woman addChildToMother(Woman mother, String childName, Gender childGender) {
        Person child = Person.builder()
                             .name(childName)
                             .gender(childGender)
                             .father(mother.getSpouse())
                             .mother(mother)
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
        Person husband = this.memberDirectory.getOrDefault(husbandName, Person.builder()
                                                                              .name(husbandName)
                                                                              .gender(Gender.M)
                                                                              .build());
        Person wife = this.memberDirectory.getOrDefault(wifeName, Person.builder()
                                                                        .name(wifeName)
                                                                        .gender(Gender.F)
                                                                        .build());
        husband.marry(wife);

        this.memberDirectory.put(husbandName, husband);
        this.memberDirectory.put(wifeName, wife);

    }

    @Override
    public boolean contains(String name) {
        return this.memberDirectory.containsKey(name);
    }

    @Override
    public String toString() {
        List<String> result = new LinkedList<>();
        Queue<Person> people = new LinkedList<>();
        people.add(this.root);

        //BFS to print all relationships

        while (!people.isEmpty()) {
            Person person = people.poll();
            Person spouse = person.getSpouse();
            StringBuilder partial = new StringBuilder(person.toString());

            if (spouse != null) {
                partial.append("<==>")
                       .append(spouse)
                       .append(spouse.getChildren());
            }
            result.add(partial.toString());
            people.addAll(person.getChildren());
        }

        return result.stream()
                     .map(line -> new StringBuilder(line).append("\n"))
                     .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                     .toString();
    }

}
