package util;

import java.util.*;

public class CustomArrayList<E> extends AbstractList<E> implements
        List<E>, RandomAccess, java.io.Serializable {
    transient Object[] array;
    private final static int DEFAULT_CAPACITY = 10;
    private int size;

    public CustomArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(Collection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                array = a;
            } else {
                array = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            array = new Object[DEFAULT_CAPACITY];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int indexOfRange(Object o, int start, int end) {
        Object[] es = array;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) Arrays.stream(array, 0, size).iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    @Override
    public <E1> E1[] toArray(E1[] a) {
        if (a.length < size)
            return (E1[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /**
     * Этот вспомогательный метод отделен от add(E), чтобы сохранить
     * размер байт-кода метода меньше 35 (значение по умолчанию -XX:MaxInlineSize),
     * что помогает, когда add(E) вызывается в цикле, скомпилированном C1.
     */
    private void add(E e, Object[] array, int s) {
        if (s == array.length)
            array = grow();
        array[s] = e;
        size = s + 1;
    }

    @Override
    public boolean add(E e) {
        add(e, array, size);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) return false;
        fastRemove(array, index);
        return true;
    }

    /**
     * Частный метод remove, который не проверяет границы массива
     * и не возвращает значения.
     */
    private void fastRemove(Object[] array, int i) {
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(array, i + 1, array, i, newSize - i);
        array[size = newSize] = null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Objects.checkIndex(index, size+1);
        Object[] newArray = c.toArray();
        int addedCount = newArray.length;
        if (addedCount == 0)
            return false;
        if (size + addedCount > array.length)
            array = grow(size + addedCount);

        int moveCount = size - index;
        if (moveCount > 0){
            System.arraycopy(array, index, array,
                    index+addedCount,
                    moveCount);
        }
        System.arraycopy(newArray, 0, array, index, addedCount);
        size += addedCount;
        return true;
    }

    @Override
    public void clear() {
        for (int to = size, i = size = 0; i < to; i++)
            array[i] = null;
    }

    @SuppressWarnings("unchecked")
    E arrayElement(int index){
        Objects.checkIndex(index, size);
        return (E) array[index];
    }

    @Override
    public E get(int index) {
        return arrayElement(index);
    }

    @Override
    public E set(int index, E element) {
        E old = arrayElement(index);
        array[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        Objects.checkIndex(index, size);
        if (size == array.length)
            grow();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        E old = arrayElement(index);
        fastRemove(array, index);
        return old;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = array.length;
        if (oldCapacity > 0) {
            int newCapacity = LocalArraysSupport.newLength(oldCapacity,
                    minCapacity - oldCapacity, /* минимальный прирост */
                    oldCapacity >> 1           /* желаемый прирост */);
            return array = Arrays.copyOf(array, newCapacity);
        } else {
            return array = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }
}
