package com.aditapillai.projects.geektrustfamily.family;

import com.aditapillai.projects.geektrustfamily.constants.Gender;

public class Families {
    public static Family createFamily(String originPersonName, Gender gender) {
        return new FamilyTree(Person.builder()
                                    .name(originPersonName)
                                    .gender(gender)
                                    .build());
    }

    public static Family createFamily(Person origin) {
        return new FamilyTree(origin);
    }
}
