package ru.seriousdim.parser.strings;

import static ru.seriousdim.parser.config.ParsingConfig.MAX_CACHE_LENGTH;
import static ru.seriousdim.parser.config.ParsingConfig.MAX_TOTAL_REQUEST_NUMBER;

/**
 * Строковые константы модуля
 */
public class Strings {

    public static final String TOTAL_REQUESTS_INVALID =
            String.format("Total requests parameter must be in range [1, %d]", MAX_TOTAL_REQUEST_NUMBER);
    public static final String CACHE_LENGTH_INVALID =
            String.format("Cache length parameter must be in the range [1, %d]", MAX_CACHE_LENGTH);
    public static final String ID_INVALID =
            "Data ID parameters must be in the range [0, 2^63 - 1]";
    public static final String EMPTY_READER_INVALID = "Cannot parse empty reader";
    public static final String AMOUNT_OF_IDS_NOT_MATCHED = "Excepted IDs: %d, got: %d";

    public static final String UNABLE_TO_PARSE_TO_NUMBER = "Unable to parse such value to number: %s";

    public static final String NO_IDS_FOUND = "No IDs found";

    public static final String EXPECTED_PARAMETERS = "Expected 2 parameters in first line. Got: %d";

}
