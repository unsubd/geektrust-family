package com.aditapillai.projects.geektrustfamily.constants;

public enum Gender {
    M, F;

    public static Gender parse(String gender) {
        return "Male".equals(gender) ? M : F;
    }
}
