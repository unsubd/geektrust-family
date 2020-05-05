package com.aditapillai.projects.geektrustfamily;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.family.Family;
import com.aditapillai.projects.geektrustfamily.utils.IOUtils;
import com.aditapillai.projects.geektrustfamily.utils.StringUtils;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        Family family = IOUtils.initializeFamily();
        List<String> input = IOUtils.readInput(args[0]);

        input.stream()
             .map(line -> line.split("\\s"))
             .forEach(split -> runCommand(family, split));

    }

    private static void runCommand(Family family, String[] split) {
        String operation = split[0];
        String personName = split[1];

        try {
            if ("GET_RELATIONSHIP".equals(operation)) {
                String relationship = split[2];
                switch (relationship) {
                    case "Paternal-Uncle":
                        System.out.println(family.getPaternalUnclesOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Maternal-Uncle":
                        System.out.println(family.getMaternalUnclesOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Paternal-Aunt":
                        System.out.println(family.getPaternalAuntsOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Maternal-Aunt":
                        System.out.println(family.getMaternalAuntsOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Sister-In-Law":
                        System.out.println(family.getSistersInLawOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Brother-In-Law":
                        System.out.println(family.getBrothersInLawOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Son":
                        System.out.println(family.getSonsOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Daughter":
                        System.out.println(family.getDaughtersOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    case "Siblings":
                        System.out.println(family.getSiblingsOf(personName)
                                                 .map(StringUtils::collectionToSpaceSeparatedString)
                                                 .orElse("NONE"));
                        break;
                    default:
                        System.out.println("UNKNOWN_OPERATION");
                }
            } else {
                family.addChild(personName, split[2], Gender.parse(split[3]));
                System.out.println("CHILD_ADDITION_SUCCEEDED");
            }

        } catch (ApiException apiException) {
            System.out.println(apiException.getMessage());
        }
    }
}
