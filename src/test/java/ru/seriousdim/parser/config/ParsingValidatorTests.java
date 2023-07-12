package ru.seriousdim.parser.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParsingValidatorTests {

    @Test
    public void testIsTotalRequestNumberValid_lessMinimum () {
        Assertions.assertFalse(
                ParsingValidator.isTotalRequestNumberValid(-100));
    }

    @Test
    public void testIsTotalRequestNumberValid_minimum () {
        Assertions.assertTrue(
                ParsingValidator.isTotalRequestNumberValid(1));
    }

    @Test
    public void testIsTotalRequestNumberValid_moreMaximum () {
        Assertions.assertFalse(
                ParsingValidator.isTotalRequestNumberValid(
                        ParsingConfig.MAX_TOTAL_REQUEST_NUMBER + 1
                ));
    }

    @Test
    public void testIsTotalRequestNumberValid_maximum () {
        Assertions.assertTrue(
                ParsingValidator.isTotalRequestNumberValid(
                        ParsingConfig.MAX_TOTAL_REQUEST_NUMBER
                ));
    }

    @Test
    public void testIsTotalRequestNumberValid_valid () {
        Assertions.assertTrue(
                ParsingValidator.isTotalRequestNumberValid(45000));
    }

    @Test
    public void testIsCacheLengthValid_lessMinimum () {
        Assertions.assertFalse(
                ParsingValidator.isCacheLengthValid(-100));
    }

    @Test
    public void testIsCacheLengthValid_minimum () {
        Assertions.assertTrue(
                ParsingValidator.isCacheLengthValid(1));
    }

    @Test
    public void testIsCacheLengthValid_moreMaximum () {
        Assertions.assertFalse(
                ParsingValidator.isCacheLengthValid(
                        ParsingConfig.MAX_CACHE_LENGTH + 1
                ));
    }

    @Test
    public void testIsCacheLengthValid_maximum () {
        Assertions.assertTrue(
                ParsingValidator.isCacheLengthValid(
                        ParsingConfig.MAX_CACHE_LENGTH
                ));
    }

    @Test
    public void testIsCacheLengthValid_valid () {
        Assertions.assertTrue(
                ParsingValidator.isCacheLengthValid(40000));
    }

    @Test
    public void testIsIdValid_lessMinimum () {
        Assertions.assertFalse(
                ParsingValidator.isIdValid(-100L));
    }

    @Test
    public void testIsIdValid_minimum () {
        Assertions.assertTrue(
                ParsingValidator.isIdValid(0L));
    }

    @Test
    public void testIsIdValid_moreMaximum () {
        Assertions.assertFalse(
                ParsingValidator.isIdValid(
                        ParsingConfig.MAX_ID_VALUE + 1
                ));
    }

    @Test
    public void testIsIdValid_maximum () {
        Assertions.assertTrue(
                ParsingValidator.isIdValid(
                        ParsingConfig.MAX_ID_VALUE
                ));
    }

    @Test
    public void testIsIdValid_valid () {
        Assertions.assertTrue(
                ParsingValidator.isIdValid(9_000_000_000_000_000L));
    }

}
