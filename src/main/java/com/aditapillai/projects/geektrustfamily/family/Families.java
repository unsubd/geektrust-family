package com.aditapillai.projects.geektrustfamily.family;

public class Families {
    public static Family createFamily(String originPerson, Gender gender) {
        return new FamilyTree(Person.builder()
                                    .name(originPerson)
                                    .gender(gender)
                                    .build());
    }
}
