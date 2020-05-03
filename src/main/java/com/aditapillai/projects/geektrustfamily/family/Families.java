package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

public class Families {
    public static Family createFamily(String originPerson, Gender gender) {
        return new FamilyTree(Person.builder()
                                    .name(originPerson)
                                    .gender(gender)
                                    .build());
    }
}
