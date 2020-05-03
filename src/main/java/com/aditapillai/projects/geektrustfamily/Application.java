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
    }
}
