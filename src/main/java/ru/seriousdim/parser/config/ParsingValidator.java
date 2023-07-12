package ru.seriousdim.parser.config;

import static ru.seriousdim.parser.config.ParsingConfig.*;

/**
 * Функции валидации численных значений для {@link ru.seriousdim.parser.InputParser}
 */
public class ParsingValidator {

    public static boolean isTotalRequestNumberValid(long totalRequest) {
        return totalRequest >= 1 &&
                totalRequest <= MAX_TOTAL_REQUEST_NUMBER;
    }

    public static boolean isCacheLengthValid(long cacheLength) {
        return cacheLength >= 1 &&
                cacheLength <= MAX_CACHE_LENGTH;
    }

    public static boolean isIdValid(Long idType) {
        return idType >= 0 &&
                idType <= MAX_ID_VALUE;
    }

}
