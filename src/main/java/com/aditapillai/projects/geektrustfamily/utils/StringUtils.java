package com.aditapillai.projects.geektrustfamily.utils;

import java.util.Collection;

public class StringUtils {
    public static <T> String collectionToSpaceSeparatedString(Collection<T> collection) {
        return collection.stream()
                         .collect(StringBuilder::new, (sb, value) -> sb.append(value)
                                                                       .append(" "), StringBuilder::append)
                         .toString()
                         .trim();
    }
}
