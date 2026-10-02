package util;

import java.util.ConcurrentModificationException;
import java.util.List;

public class TestMultithreds { // TODO: Удалить этот временный класс, написать нормальные юнит-тесты
    public static <T> void test(List<T> list, T newElement) throws InterruptedException {
        System.out.println("Запуск теста безопасности modCount...");

        // 2. Поток для параллельного подсчета элементов
        Thread readerThread = new Thread(() -> {
            try {
                long count = list.parallelStream()
                    .filter(el -> {
                        // Искусственная микро-задержка, чтобы дать второму потоку
                        // время гарантированно вмешаться во время работы стрима
                        try {
                            Thread.sleep(0, 100);
                        } catch (Exception ignored) {
                        }
                        return "Apple".equals(el);
                    })
                    .count();

                System.out.println("❌ ТЕСТ ПРОВАЛЕН: Стрим завершился без ошибки. Результат: " + count);
            } catch (ConcurrentModificationException e) {
                System.out.println("✅ ТЕСТ УСПЕШНО ПРОЙДЕН! Поймано ожидаемое исключение: " + e.getClass().getName());
            } catch (Exception e) {
                System.out.println("❌ ТЕСТ ПРОВАЛЕН: Выпало НЕ ТО исключение: " + e);
            }
        });

        // 3. Поток для несанкционированного изменения коллекции
        Thread writerThread = new Thread(() -> {
            try {
                // Немного ждем, чтобы стрим успел запуститься и нарезать задачи
                Thread.sleep(10);
                list.add(newElement); // Ломаем структуру списка прямо во время чтения
                System.out.println("Поток-писатель успешно добавил элемент.");
            } catch (InterruptedException ignored) {
            }
        });

        // Запуск проверки
        readerThread.start();
        writerThread.start();

        // Ждем завершения обоих потоков
        readerThread.join();
        writerThread.join();
    }
}
