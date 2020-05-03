package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.errors.Errors;

import java.util.*;
import java.util.stream.Collectors;

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
        this.validateName(motherName);
        Optional.of(this.memberDirectory.get(motherName))
                .filter(person -> person instanceof Woman)
                .map(person -> (Woman) person)
                .map(mother -> addChildToMother(mother, childName, childGender))
                .orElseThrow(() -> new ApiException(Errors.CHILD_ADDITION_FAILED_ERROR_MESSAGE));
    }

    @Override
    public Optional<Set<String>> getSonsOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .map(Person::getChildren)
                       .map(children -> children.stream()
                                                .filter(Person::isMale)
                                                .map(person -> person.name)
                                                .collect(Collectors.toSet()))
                       .filter(children -> !children.isEmpty());
    }

    @Override
    public Optional<Set<String>> getDaughtersOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .map(Person::getChildren)
                       .map(children -> children.stream()
                                                .filter(Person::isFemale)
                                                .map(person -> person.name)
                                                .collect(Collectors.toSet()))
                       .filter(children -> !children.isEmpty());
    }

    @Override
    public Optional<Set<String>> getSiblingsOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.mother))
                       .map(Person::getChildren)
                       .map(children -> children.stream()
                                                .map(person -> person.name)
                                                .filter(person -> !person.equals(name))
                                                .collect(Collectors.toSet()))
                       .filter(children -> !children.isEmpty());

    }

    @Override
    public Optional<String> getMotherOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.mother))
                       .map(person -> person.name);
    }

    @Override
    public Optional<String> getFatherOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.father))
                       .map(person -> person.name);
    }

    @Override
    public Optional<Set<String>> getPaternalUnclesOf(String name) {
        this.validateName(name);

        return Optional.of(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.father))
                       .flatMap(person -> Optional.ofNullable(person.father))
                       .map(person -> person.getChildren()
                                            .stream()
                                            .filter(Person::isMale)
                                            .map(child -> child.name)
                                            .collect(Collectors.toSet()));
    }

    private void validateName(String name) {
        if (!this.contains(name)) {
            throw new ApiException(Errors.PERSON_NOT_FOUND_ERROR_MESSAGE);
        }
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
