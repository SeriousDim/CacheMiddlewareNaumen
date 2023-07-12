package ru.seriousdim.parser;

import ru.seriousdim.parser.basic.IParseable;
import ru.seriousdim.parser.config.ParsingValidator;
import ru.seriousdim.parser.exceptions.ParsingException;
import ru.seriousdim.parser.model.InputData;
import static ru.seriousdim.parser.strings.Strings.*;

import java.io.BufferedReader;

/**
 * Парсер данных для задания из input.txt
 */
public class InputParser implements IParseable<Long> {

    /**
     * Проверяет, является ли параметр в первой строке числом и
     * преобразует его в число
     * @param param параметр из певрой строки
     * @return параметр в виде числа
     * @throws ParsingException если параметр не число
     */
    public Integer parseParam(String param) throws ParsingException {
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            throw new ParsingException(
                    String.format(UNABLE_TO_PARSE_TO_NUMBER, param)
            );
        }
    }

    /**
     * Парсит первую строку данных, которая содержит 2 параметра:
     * размер кэша и кол-во запросов
     * @param line первая строка данных
     * @return массив с числами: первое - размер кэша, второе - кол-во запросов
     * @throws ParsingException если 1) указано не 2 параметра,
     * 2) параметры - не числа, 3) значения параметров невалидны
     */
    public Integer[] parseFirstLine (String line) throws ParsingException {
        var firstLineParams = line.split(" ");

        if (firstLineParams.length != 2) {
            throw new ParsingException(
                    String.format(EXPECTED_PARAMETERS, firstLineParams.length)
            );
        }

        var cacheLength = this.parseParam(firstLineParams[0]);
        var totalRequests = this.parseParam(firstLineParams[1]);

        if (!ParsingValidator.isCacheLengthValid(cacheLength)) {
            throw new ParsingException(CACHE_LENGTH_INVALID);
        }
        if (!ParsingValidator.isTotalRequestNumberValid(totalRequests)) {
            throw new ParsingException(TOTAL_REQUESTS_INVALID);
        }

        return new Integer[] {cacheLength, totalRequests};
    }

    /**
     * Парсит идентификатор (id) запроса
     * @param line строка с id
     * @return id в виде числа
     * @throws ParsingException если 1) id невалидно,
     * 2) id - не число
     */
    public Long parseId (String line) throws ParsingException {
        try {
            var id = Long.parseLong(line);

            if (!ParsingValidator.isIdValid(id)) {
                throw new ParsingException(ID_INVALID);
            }

            return id;
        } catch (NumberFormatException e) {
            throw new ParsingException(String.format(
                    UNABLE_TO_PARSE_TO_NUMBER,
                    line
            ));
        }
    }

    /**
     * Сравнивает заявленное кол-во идентификаторов (id) запросов в первой строке данных и
     * кол-во по факту перечисленных id
     * @param idsAmount кол-во перечисленных id
     * @param totalRequests кол-во заявленных id в первой строке данных
     * @throws ParsingException если есть несоответствие
     */
    public void checkIdAmount (int idsAmount, int totalRequests) throws ParsingException {
        if (idsAmount != totalRequests) {
            throw new ParsingException(
                    String.format(
                            AMOUNT_OF_IDS_NOT_MATCHED,
                            totalRequests,
                            idsAmount
                    ));
        }
    }

    /**
     * Парсит все данные из файла
     * @param reader {@link BufferedReader} с содержимым файла
     * @return {@link InputData}
     * @throws ParsingException
     */
    public InputData<Long> parse(BufferedReader reader)
            throws ParsingException
    {
        return this.parse(reader.lines().toList());
    }

    /**
     * Парсит все данные из коллекции со строками
     * @param strings коллекция со строкми
     * @return {@link InputData}
     * @throws ParsingException
     */
    @Override
    public InputData<Long> parse(Iterable<String> strings) throws ParsingException {
        var result = new InputData<Long>();

        var iterator = strings.iterator();

        if (!iterator.hasNext()) {
            throw new ParsingException(EMPTY_READER_INVALID);
        }

        var parsedParams = this.parseFirstLine(iterator.next());

        result.setCacheLength(parsedParams[0]);
        result.setTotalRequests(parsedParams[1]);

        if (!iterator.hasNext()) {
            throw new ParsingException(NO_IDS_FOUND);
        }

        while (iterator.hasNext()) {
            var line = iterator.next();
            var parsedId = parseId(line);

            result.appendId(parsedId);
        }

        this.checkIdAmount(result.getIds().size(), parsedParams[1]);

        return result;
    }
}
