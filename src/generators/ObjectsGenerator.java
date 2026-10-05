package generators;

import java.util.List;

/**
 * Интерфейс для рандомной (или нет) генерации массива объектов
 * @param <T>
 */
public interface ObjectsGenerator<T> {
    void generate(List<T> targetList, int length);
}
