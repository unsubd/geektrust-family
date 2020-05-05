package com.aditapillai.projects.geektrustfamily;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.family.Family;
import com.aditapillai.projects.geektrustfamily.utils.IOUtils;
import com.aditapillai.projects.geektrustfamily.utils.QueryHelper;
import com.aditapillai.projects.geektrustfamily.utils.StringUtils;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        Family family = IOUtils.initializeFamily();
        List<String> input = IOUtils.readInput(args[0]);

        input.stream()
             .map(line -> line.split("\\s"))
             .forEach(split -> executeInputOperations(family, split));

    }

    private static void executeInputOperations(Family family, String[] split) {
        String operation = split[0];
        String personName = split[1];
        try {
            if ("GET_RELATIONSHIP".equals(operation)) {
                String relationship = split[2];
                System.out.println(QueryHelper.getQueryFunction(relationship, family)
                                              .apply(personName)
                                              .map(StringUtils::collectionToSpaceSeparatedString)
                                              .orElse("NONE"));
            } else {
                family.addChild(personName, split[2], Gender.parse(split[3]));
                System.out.println("CHILD_ADDITION_SUCCEEDED");
            }

        } catch (ApiException apiException) {
            System.out.println(apiException.getMessage());
        }
    }
}
