package generators;

import java.util.List;

/**
 * Интерфейс для рандомной (или нет) генерации массива объектов
 * @param <T>
 */
public interface ObjectsGenerator<T> {
    //List<T> generate(int length);
    void generate(List<T> targetList, int length);

}
