package test;

import util.CustomArrayList;

import java.util.*;

public class Test {
    public static void testCustomArrayList(){
        new ArrayList<>();
        List<Integer> cal = new CustomArrayList<>();
        assertion(cal.isEmpty());
        cal.add(3);
        cal.add(7);
        cal.add(1);
        assertion(cal.size() == 3);
        assertion(cal.get(1) == 7);
        assertion(cal.contains(7));
        assertion(cal.remove((Object)7));
        assertion(cal.size() == 2);
        assertion(cal.get(1) == 1);
        assertion(!cal.remove((Object)1123));
        assertion(cal.remove(0) == 3);
        assertion(cal.get(0) == 1);
        assertionThrow(IndexOutOfBoundsException.class, () -> cal.get(10));
        assertionThrow(IndexOutOfBoundsException.class, () -> cal.remove(-1));
        assertion(cal.addAll(List.of(5,4,3,2,1)));
        // Элементы на данный момент: 1,5,4,3,2,1
        assertion(cal.size() == 6);
        assertion(cal.get(1) == 5 && cal.get(cal.size()-1) == 1);
        assertion(cal.indexOf(3) == 3);
        assertion(cal.set(4, 10) == 2);
        assertion(cal.addAll(1,List.of(6,6,6)));
        assertion(Arrays.equals(cal.toArray(), new Object[]{1, 6, 6, 6, 5, 4, 3, 10, 1}));
        for (Integer i : cal) {
            assertion(cal.contains(i));
        }
        assertion(Arrays.equals(
                cal.toArray(new Integer[3]),
                new Integer[]{1, 6, 6, 6, 5, 4, 3, 10, 1}));
        assertion(Arrays.equals(
                cal.toArray(new Integer[11]),
                new Integer[]{1, 6, 6, 6, 5, 4, 3, 10, 1, null, null}));
        Object[] collect = cal.stream().distinct().toArray();
        assertion(Arrays.equals(collect, new Object[]{1, 6, 5, 4, 3, 10}));
        cal.sort(Comparator.comparingInt(Integer::intValue));
        assertion(Arrays.equals(cal.toArray(), new Integer[]{1, 1, 3, 4, 5, 6, 6, 6, 10}));
        cal.addAll(cal);
        cal.addAll(cal);
        assertion(cal.size() == 36);
        cal.add(null);
        cal.clear();
        assertion(cal.isEmpty());
    }

    public static void assertion(boolean statement){
        if (!statement) throw new AssertionError("Неверное утверждение");
    }

    public static <T extends Exception> void assertionThrow(Class<T> thr, Runnable statement){
        Exception exception = new Exception();
        try {
            statement.run();
        } catch (Exception e) {
            exception = e;
        }
        assertion(exception.getClass().equals(thr));
    }
}
