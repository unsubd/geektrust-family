package com.aditapillai.projects.geektrustfamily.constants;

public enum Gender {
    M, F;

    public static Gender parse(String gender) {
        return "Female".equals(gender) ? F : M;
    }
}
