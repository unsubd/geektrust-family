package com.aditapillai.projects.geektrustfamily.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    public void collectionToString_ValidCollection_StringRepresentationReturned() {
        String result = StringUtils.collectionToSpaceSeparatedString(Arrays.asList("Val1", "Val2"));
        String expected = "Val1 Val2";
        assertEquals(expected, result);
    }
}