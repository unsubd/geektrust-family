package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.errors.Errors;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Optional<? extends Set<String>> getSonsOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .map(Person::getChildren)
                       .map(children -> children.stream()
                                                .filter(Person::isMale)
                                                .map(son -> son.name)
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(children -> !children.isEmpty());
    }

    @Override
    public Optional<? extends Set<String>> getDaughtersOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .map(Person::getChildren)
                       .map(children -> children.stream()
                                                .filter(Person::isFemale)
                                                .map(daughter -> daughter.name)
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(children -> !children.isEmpty());
    }

    @Override
    public Optional<? extends Set<String>> getSiblingsOf(String name) {
        this.validateName(name);
        return Optional.ofNullable(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.mother))
                       .map(Person::getChildren)
                       .map(children -> children.stream()
                                                .map(child -> child.name)
                                                .filter(child -> !child.equals(name))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(children -> !children.isEmpty());

    }

    @Override
    public Optional<? extends Set<String>> getPaternalUnclesOf(String name) {
        this.validateName(name);
        Person currentPerson = this.memberDirectory.get(name);
        return Optional.of(currentPerson)
                       .flatMap(person -> Optional.ofNullable(person.father))
                       .flatMap(father -> Optional.ofNullable(father.father))
                       .map(grandFather -> grandFather.getChildren()
                                                      .stream()
                                                      .filter(Person::isMale)
                                                      .filter(uncle -> !uncle.equals(currentPerson.father))
                                                      .map(child -> child.name)
                                                      .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(uncles -> !uncles.isEmpty());
    }

    @Override
    public Optional<? extends Set<String>> getMaternalUnclesOf(String name) {
        this.validateName(name);
        return Optional.of(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.mother))
                       .flatMap(mother -> Optional.ofNullable(mother.mother))
                       .map(grandMother -> grandMother.getChildren()
                                                      .stream()
                                                      .filter(Person::isMale)
                                                      .map(child -> child.name)
                                                      .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(uncles -> !uncles.isEmpty());

    }

    @Override
    public Optional<? extends Set<String>> getPaternalAuntsOf(String name) {
        this.validateName(name);
        return Optional.of(this.memberDirectory.get(name))
                       .flatMap(person -> Optional.ofNullable(person.father))
                       .flatMap(father -> Optional.ofNullable(father.father))
                       .map(grandFather -> grandFather.getChildren()
                                                      .stream()
                                                      .filter(Person::isFemale)
                                                      .map(child -> child.name)
                                                      .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(aunts -> !aunts.isEmpty());
    }

    @Override
    public Optional<? extends Set<String>> getMaternalAuntsOf(String name) {
        this.validateName(name);
        Person currentPerson = this.memberDirectory.get(name);
        return Optional.of(currentPerson)
                       .flatMap(person -> Optional.ofNullable(person.mother))
                       .flatMap(mother -> Optional.ofNullable(mother.mother))
                       .map(grandMother -> grandMother.getChildren()
                                                      .stream()
                                                      .filter(Person::isFemale)
                                                      .filter(aunt -> !aunt.equals(currentPerson.mother))
                                                      .map(child -> child.name)
                                                      .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(aunts -> !aunts.isEmpty());

    }

    @Override
    public Optional<? extends Set<String>> getSistersInLawOf(String name) {
        this.validateName(name);
        Person currentPerson = this.memberDirectory.get(name);
        Stream<Person> sistersOfSpouse = Optional.of(currentPerson)
                                                 .flatMap(person -> Optional.ofNullable(person.getSpouse()))
                                                 .flatMap(spouse -> Optional.ofNullable(spouse.mother))
                                                 .map(motherInLaw -> motherInLaw.getChildren()
                                                                                .stream()
                                                                                .filter(Person::isFemale)
                                                                                .filter(person -> !Objects.equals(currentPerson.getSpouse(), person)))
                                                 .orElse(Stream.empty());
        Stream<Person> wivesOfSiblings = Optional.of(currentPerson)
                                                 .flatMap(person -> Optional.ofNullable(person.mother))
                                                 .map(mother -> mother.getChildren()
                                                                      .stream()
                                                                      .filter(person -> !currentPerson.equals(person))
                                                                      .filter(Person::isMale)
                                                                      .map(Person::getSpouse)
                                                                      .filter(Objects::nonNull))
                                                 .orElse(Stream.empty());

        return Optional.of(Stream.concat(sistersOfSpouse, wivesOfSiblings)
                                 .map(sisterInLaw -> sisterInLaw.name)
                                 .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(sistersInLaw -> !sistersInLaw.isEmpty());
    }

    @Override
    public Optional<? extends Set<String>> getBrothersInLawOf(String name) {
        this.validateName(name);
        Person currentPerson = this.memberDirectory.get(name);
        Stream<Person> brothersOfSpouse = Optional.of(currentPerson)
                                                  .flatMap(person -> Optional.ofNullable(person.getSpouse()))
                                                  .flatMap(spouse -> Optional.ofNullable(spouse.mother))
                                                  .map(motherInLaw -> motherInLaw.getChildren()
                                                                                 .stream()
                                                                                 .filter(Person::isMale)
                                                                                 .filter(person -> !Objects.equals(currentPerson.getSpouse(), person)))
                                                  .orElse(Stream.empty());
        Stream<Person> husbandsOfSiblings = Optional.of(currentPerson)
                                                    .flatMap(person -> Optional.ofNullable(person.mother))
                                                    .map(mother -> mother.getChildren()
                                                                         .stream()
                                                                         .filter(person -> !currentPerson.equals(person))
                                                                         .filter(Person::isFemale)
                                                                         .map(Person::getSpouse)
                                                                         .filter(Objects::nonNull))
                                                    .orElse(Stream.empty());

        return Optional.of(Stream.concat(brothersOfSpouse, husbandsOfSiblings)
                                 .map(brotherInLaw -> brotherInLaw.name)
                                 .collect(Collectors.toCollection(LinkedHashSet::new)))
                       .filter(brothersInLaw -> !brothersInLaw.isEmpty());
    }

    @Override
    public void hostWedding(String husbandName, String wifeName) {
        if (!this.contains(husbandName) && !this.contains(wifeName)) {
            throw new ApiException(Errors.ONE_MEMBER_SHOULD_BE_FAMILY_ERROR_MESSAGE);
        }
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
    public Optional<Person> getPerson(String name) {
        return Optional.ofNullable(this.memberDirectory.get(name));
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
