package com.aditapillai.projects.geektrustfamily;

import com.aditapillai.projects.geektrustfamily.family.Family;
import com.aditapillai.projects.geektrustfamily.utils.IOUtils;
import com.aditapillai.projects.geektrustfamily.utils.StringUtils;

public class Application {
    public static void main(String[] args) {
        Family family = IOUtils.initializeFamily();
        System.out.println(family.getSonsOf("King Shan")
                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                 .orElse("NONE"));
        System.out.println(family.getDaughtersOf("King Shan")
                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                 .orElse("NONE"));

        System.out.println(family.getSiblingsOf("Satya")
                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                 .orElse("NONE"));

        System.out.println(family.getMotherOf("Satya")
                                 .orElse("NONE"));

        System.out.println(family.getFatherOf("Satya")
                                 .orElse("NONE"));

        System.out.println(family.getPaternalUnclesOf("Jnki")
                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                 .orElse("NONE"));

        System.out.println(family.getMaternalUnclesOf("Yodhan")
                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                 .orElse("NONE"));

        System.out.println(family.getSistersInLawOf("Satya")
                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                 .orElse("NONE"));

    }
}
