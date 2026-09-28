package io;

import util.CustomArrayList;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class File extends java.io.File {
    public File(String pathname) {
        super(pathname);
    }

    /**
     * Записывает данные объекта в файл, перезаписывая его.
     * @param fileName имя файла
     * @param obj добавляемый объект
     * @return true, если объект записан, а иначе false
     * @param <T> Объект, реализующий интерфейс {@link Serializable}
     */
    public static <T extends Serializable> boolean write(String fileName, T obj) {
        return write(fileName, new Object[]{obj});
    }

    /**
     * Записывает данные объектов в файл, перезаписывая его.
     * @param fileName имя файла
     * @param obj добавляемые объекты
     * @return true, если все объекты записаны, а иначе false
     */
    public static boolean write(String fileName, Object[] obj) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            for (Object o : obj) {
                try {
                    oos.writeObject(o);
                } catch (NotSerializableException e) {
                    System.out.printf("Некоторые объекты файла '%s' не реализовывают интерфейс Serializable: %s\n",
                            fileName, e.getLocalizedMessage());
                } catch (InvalidClassException e) {
                    System.out.printf("Что-то пошло не так с указанным классом %s при сериализации в файл '%s': %s\n",
                            o.getClass(), fileName, e.getLocalizedMessage());
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.printf("Файл '%s' не найден и не может быть создан или открыт\n", fileName);
        } catch (SecurityException e) {
            System.out.printf("Доступ на открытие файла '%s' отклонён системой\n", fileName);
        } catch (IOException e) {
            System.out.printf("Что-то пошло не так при записи в файл '%s', проверьте, пожалуйста, файл\n",
                    fileName);
        }
        return false;
    }

    /**
     * Находит в файле записанный объект и возвращает его.
     * @param type тип объекта
     * @param fileName имя файла
     * @return Optional объекта типа T, если найден, а иначе {@link Optional#empty()}
     * @param <T> Объект, реализующий интерфейс {@link Serializable}
     */
    public static <T extends Serializable> Optional<T> read(Class<T> type, String fileName) {
        List<T> res = read(type, fileName, false);
        return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
    }

    /**
     * Находит в файле записанные объекты и возвращает их список.
     * @param type тип объекта
     * @param fileName имя файла
     * @return {@link util.CustomArrayList} список Optional объекта типа T, если найден, а иначе {@link Optional#empty()}
     * @param <T> Объект, реализующий интерфейс {@link Serializable}
     */
    public static <T extends Serializable> List<T> readAll(Class<T> type, String fileName) {
        return read(type, fileName, true);
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> read(Class<T> type, String fileName, boolean multiple){
        List<T> result = new CustomArrayList<>();
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj;
            boolean stop = false;
            while (!stop) {
                try {
                    obj = ois.readObject();
                    if (obj.getClass() == type) {
                        result.add((T) obj);
                        if (!multiple)
                            return result;
                    }
                } catch (InvalidClassException e) {
                    stop = true;
                    System.out.printf("Что-то пошло не так с указанным классом %s при десериализации из файла '%s': %s\n",
                            type, fileName, e.getLocalizedMessage());
                } catch (ClassNotFoundException e) {
                    stop = true;
                    System.out.printf("Класс сериализованного объекта из файла '%s' не найден: %s\n",
                            fileName, e.getLocalizedMessage());
                } catch (OptionalDataException e) {
                    stop = true;
                    System.out.printf("Был найден примитивный тип вместо объекта в файле '%s'\n", fileName);
                } catch (StreamCorruptedException e) {
                    stop = true;
                    System.out.printf("Данные объектов файл '%s' повреждены: %s\n",
                            fileName, e.getLocalizedMessage());
                } catch (IOException ignored) {
                    stop = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Файл '%s' не найден или не может быть открыт\n", fileName);
        } catch (SecurityException e) {
            System.out.printf("Доступ на открытие файла '%s' отклонён системой\n", fileName);
        } catch (StreamCorruptedException e) {
            System.out.printf("Файл '%s' не может быть считан, т.к. не имеет заголовка: %s\n",
                    fileName, e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.printf("Что-то пошло не так при считывании файла '%s', проверьте, пожалуйста, файл\n",
                    fileName);
        }
        return result;
    }
}
