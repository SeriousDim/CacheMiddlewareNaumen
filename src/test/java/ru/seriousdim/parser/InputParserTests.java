package ru.seriousdim.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.seriousdim.parser.exceptions.ParsingException;
import ru.seriousdim.parser.strings.Strings;

import java.util.ArrayList;
import java.util.Arrays;

public class InputParserTests {

    @Test
    public void testParse_empty () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>();
            parser.parse(strings);
        }, Strings.EMPTY_READER_INVALID);
    }

    @Test
    public void testParse_emptyFirstLine () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("");
            }};
            parser.parse(strings);
        }, Strings.EMPTY_READER_INVALID);
    }

    @Test
    public void testParse_lessTwoParamsInFirstLine () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("2");
            }};
            parser.parse(strings);
        }, String.format(Strings.EXPECTED_PARAMETERS, 1));
    }

    @Test
    public void testParse_moreTwoParamsInFirstLine () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("2 2 4");
            }};
            parser.parse(strings);
        }, String.format(Strings.EXPECTED_PARAMETERS, 1));
    }

    @Test
    public void testParse_invalidCacheLength () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("-23 4");
            }};
            parser.parse(strings);
        }, Strings.CACHE_LENGTH_INVALID);
    }

    @Test
    public void testParse_invalidTotalRequests () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("50 120000");
            }};
            parser.parse(strings);
        }, Strings.TOTAL_REQUESTS_INVALID);
    }

    @Test
    public void testParse_noIds () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("20 50");
            }};
            parser.parse(strings);
        }, Strings.NO_IDS_FOUND);
    }

    @Test
    public void testParse_invalidId () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = new ArrayList<String>() {{
                add("2 2");
                add("3");
                add("-100");
            }};
            parser.parse(strings);
        }, Strings.ID_INVALID);
    }

    @Test
    public void testParse_amountOfIdsNotMatched () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = Arrays.asList(
                    "2 2", "3", "4", "5"
            );
            parser.parse(strings);
        }, String.format(
                Strings.AMOUNT_OF_IDS_NOT_MATCHED,
                2,
                3
        ));
    }

    @Test
    public void testParse_invalidCharacters () {
        Assertions.assertThrows(ParsingException.class, () -> {
            var parser = new InputParser();
            var strings = Arrays.asList(
                    "2 2", "3", "abc"
            );
            parser.parse(strings);
        }, String.format(
                Strings.UNABLE_TO_PARSE_TO_NUMBER,
                "abc"
        ));
    }

    @Test
    public void testParse_valid () throws ParsingException {
        var parser = new InputParser();
        var strings = Arrays.asList(
                "5 10", "2", "34", "3", "450", "900000000000000",
                "100000", "1", "35", "67", "3"
        );
        var data = parser.parse(strings);

        Assertions.assertEquals(5, data.getCacheLength());
        Assertions.assertEquals(10, data.getTotalRequests());

        Assertions.assertArrayEquals(
                new Long[] {2L, 34L, 3L, 450L, 900000000000000L,
                        100000L, 1L, 35L, 67L, 3L},
                data.getIds().toArray()
        );
    }

}
