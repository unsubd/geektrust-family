package com.aditapillai.projects.geektrustfamily.utils;

import com.aditapillai.projects.geektrustfamily.errors.ApiException;
import com.aditapillai.projects.geektrustfamily.errors.Errors;
import com.aditapillai.projects.geektrustfamily.family.Family;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class QueryHelper {

    public static Function<String, Optional<? extends Set<String>>> getQueryFunction(String relationship, Family family) {
        Function<String, Optional<? extends Set<String>>> function;

        switch (relationship) {
            case "Paternal-Uncle":
                function = family::getPaternalUnclesOf;
                break;
            case "Maternal-Uncle":
                function = family::getMaternalUnclesOf;

                break;
            case "Paternal-Aunt":
                function = family::getPaternalAuntsOf;
                break;
            case "Maternal-Aunt":
                function = family::getMaternalAuntsOf;
                break;
            case "Sister-In-Law":
                function = family::getSistersInLawOf;
                break;
            case "Brother-In-Law":
                function = family::getBrothersInLawOf;
                break;
            case "Son":
                function = family::getSonsOf;
                break;
            case "Daughter":
                function = family::getDaughtersOf;
                break;
            case "Siblings":
                function = family::getSiblingsOf;
                break;
            default:
                throw new ApiException(Errors.UNKNOWN_OPERATION_ERROR_MESSAGE);
        }

        return function;

    }
}
