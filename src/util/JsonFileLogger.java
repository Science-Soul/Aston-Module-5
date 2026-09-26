package util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Утилита для записи отсортированных коллекций и найденных значений
 * в JSON-файлы в каталоге resources/data.
 * <p>
 * Каждый файл на диске — это один валидный JSON-документ: JSON-массив
 * {@code [ {...}, {...}, ... ]}, где каждый элемент — запись о сортировке
 * или найденном значении. Так как дописать элемент в конец JSON-массива
 * простым дозаписыванием байт нельзя (нужно убрать закрывающую "]"),
 * при каждом вызове файл читается целиком, в него вставляется новый
 * элемент перед закрывающей скобкой массива, и файл перезаписывается
 * (read-modify-write). Существующие записи при этом не теряются и не меняются.
 */
public final class JsonFileLogger {

    private static final Path DATA_DIR = Path.of("resources", "data");

    private JsonFileLogger() {
    }

    /**
     * Добавляет в JSON-массив файла {@code fileName} (внутри resources/data) запись
     * об отсортированной коллекции.
     *
     * @param sorted   отсортированная коллекция для сохранения
     * @param fileName имя файла, например "sorted_lists.json"
     */
    public static void logSortedCollection(Collection<?> sorted, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"sorted_collection\",");
        sb.append("\"timestamp\":\"").append(LocalDateTime.now()).append("\",");
        sb.append("\"size\":").append(sorted.size()).append(",");
        sb.append("\"data\":").append(toJsonArray(sorted));
        sb.append("}");
        appendToJsonArray(fileName, sb.toString());
    }

    /**
     * Добавляет в JSON-массив файла {@code fileName} (внутри resources/data) запись
     * о результате поиска значения (например, indexOf/contains).
     *
     * @param query    что искали
     * @param result   что нашли (индекс, boolean, сам элемент и т.п.)
     * @param fileName имя файла, например "found_values.json"
     */
    public static void logFoundValue(Object query, Object result, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"found_value\",");
        sb.append("\"timestamp\":\"").append(LocalDateTime.now()).append("\",");
        sb.append("\"query\":").append(toJsonValue(query)).append(",");
        sb.append("\"result\":").append(toJsonValue(result));
        sb.append("}");
        appendToJsonArray(fileName, sb.toString());
    }

    /**
     * Вставляет {@code jsonObject} в качестве нового элемента JSON-массива,
     * хранящегося в файле {@code fileName}. Если файла ещё нет или он пуст,
     * создаёт новый массив с единственным элементом.
     * <p>
     * Файл при каждом вызове полностью перезаписывается: старые элементы
     * читаются, к ним добавляется новый, и результат записывается заново.
     * Каждая запись (объект верхнего уровня) располагается на нескольких
     * строках — по одному полю на строку ({@code "type": ...}, {@code "timestamp": ...}
     * и т.д.), а значения-массивы (например {@code "data":[...]}) остаются
     * компактными, в одну строку.
     */
    private static synchronized void appendToJsonArray(String fileName, String jsonObject) {
        try {
            Files.createDirectories(DATA_DIR);
            Path file = DATA_DIR.resolve(fileName);

            String existing = Files.exists(file) ? Files.readString(file) : "[]";
            String compactExisting = compact(existing);
            if (compactExisting.isEmpty()) {
                compactExisting = "[]";
            }

            List<String> elements = splitTopLevelArrayElements(compactExisting);
            elements.add(jsonObject);

            StringBuilder updated = new StringBuilder("[\n");
            for (int i = 0; i < elements.size(); i++) {
                updated.append(prettyPrintObject(elements.get(i), 1));
                if (i < elements.size() - 1) updated.append(",");
                updated.append("\n");
            }
            updated.append("]");

            Files.writeString(
                    file,
                    updated.toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось записать данные в файл: " + fileName, e);
        }
    }

    /**
     * Убирает из JSON-строки все пробельные символы, находящиеся вне строковых литералов.
     */
    private static String compact(String json) {
        StringBuilder result = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }
            if (!inQuotes && Character.isWhitespace(c)) {
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }

    /**
     * Разбирает компактную строку JSON-массива {@code [e1,e2,e3]} на список
     * его элементов верхнего уровня (сами элементы остаются компактными).
     */
    private static List<String> splitTopLevelArrayElements(String compactArray) {
        if (compactArray.equals("[]")) {
            return new ArrayList<>();
        }
        String inner = compactArray.substring(1, compactArray.length() - 1);
        return splitTopLevelByComma(inner);
    }

    /**
     * Форматирует компактный JSON-объект {@code {"k1":v1,"k2":v2,...}} так,
     * чтобы каждое поле находилось на отдельной строке с отступом, соответствующим
     * {@code indentLevel}. Значения-массивы и прочие значения (числа, строки,
     * null) остаются компактными, в одну строку; значения-объекты форматируются
     * рекурсивно тем же образом.
     */
    private static String prettyPrintObject(String compactValue, int indentLevel) {
        if (!compactValue.startsWith("{")) {
            // Массивы, строки, числа, null, boolean — оставляем как есть, в одну строку
            return compactValue;
        }
        String inner = compactValue.substring(1, compactValue.length() - 1);
        if (inner.isEmpty()) {
            return "{}";
        }

        List<String> fields = splitTopLevelByComma(inner);
        String fieldIndent = "  ".repeat(indentLevel);
        String closingIndent = "  ".repeat(indentLevel - 1);

        StringBuilder sb = new StringBuilder("{\n");
        for (int i = 0; i < fields.size(); i++) {
            String[] keyValue = splitKeyValue(fields.get(i));
            sb.append(fieldIndent)
                    .append(keyValue[0])
                    .append(": ")
                    .append(prettyPrintObject(keyValue[1], indentLevel + 1));
            if (i < fields.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append(closingIndent).append("}");
        return sb.toString();
    }

    /**
     * Разбивает содержимое {@code "key":value} на ключ (вместе с кавычками)
     * и значение. Предполагается, что ключ — простая строка без экранированных
     * кавычек внутри.
     */
    private static String[] splitKeyValue(String field) {
        int closingQuote = 1;
        while (!(field.charAt(closingQuote) == '"' && field.charAt(closingQuote - 1) != '\\')) {
            closingQuote++;
        }
        String key = field.substring(0, closingQuote + 1);
        String value = field.substring(closingQuote + 2); // пропускаем закрывающую кавычку и ':'
        return new String[]{key, value};
    }

    /**
     * Разбивает содержимое объекта/массива (без внешних скобок) на элементы
     * верхнего уровня, разделённые запятыми вне строк и вне вложенных
     * скобок/фигурных скобок.
     */
    private static List<String> splitTopLevelByComma(String content) {
        List<String> elements = new ArrayList<>();
        int depth = 0;
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '"' && (i == 0 || content.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }
            if (!inQuotes) {
                if (c == '{' || c == '[') depth++;
                else if (c == '}' || c == ']') depth--;
            }
            if (c == ',' && depth == 0 && !inQuotes) {
                elements.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        if (!current.isEmpty()) {
            elements.add(current.toString());
        }
        return elements;
    }

    private static String toJsonArray(Collection<?> collection) {
        StringBuilder sb = new StringBuilder("[");
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            sb.append(toJsonValue(it.next()));
            if (it.hasNext()) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String toJsonValue(Object value) {
        if (value == null) return "null";
        if (value instanceof JsonSerializable serializable) return serializable.toJson();
        if (value instanceof Number || value instanceof Boolean) return value.toString();
        return "\"" + escape(value.toString()) + "\"";
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}