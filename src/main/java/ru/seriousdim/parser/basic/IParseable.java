package ru.seriousdim.parser.basic;

import ru.seriousdim.parser.exceptions.ParsingException;
import ru.seriousdim.parser.model.InputData;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Интерфейс для парсеров данных для задания
 * @param <IdType> тип идентификатора (id) запроса
 */
public interface IParseable<IdType> {

    InputData<IdType> parse(BufferedReader reader) throws ParsingException, IOException;

    InputData<IdType> parse(Iterable<String> strings) throws ParsingException;

}
