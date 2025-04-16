package ru.mentee.power.collections.list;

import java.util.*;

public class LinkedListAnalyzer {

    public static <T> List<T> mergeListsPreserveOrder(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Один из списков list1 или list2 - null");
        }

        Set<T> seen = new HashSet<>();
        List<T> result = new LinkedList<>();

        for (T item : list1) {
            if (seen.add(item)) {
                result.add(item);
            }
        }

        for (T item : list2) {
            if (seen.add(item)) {
                result.add(item);
            }
        }

        return result;
    }

    public static <T> List<T> reverse(List<T> list) {
        List<T> reversedList = new ArrayList<>();

        if (list == null) {
            throw new IllegalArgumentException("Список null");
        }

        if (list.size() <= 1) {
            return list;
        }

        for (T element : list) {
            reversedList.addFirst(element);
        }

        list.clear();
        list.addAll(reversedList);

        return list;
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список null");
        }

        List<T> result = new ArrayList<>();
        Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()) {
            T current = iterator.next();
            if (!result.contains(current)) {
                result.add(current);
            }
        }

        list.clear();
        list.addAll(result);
        return list;
    }

    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list == null) {
            throw new IllegalArgumentException("Список null");
        }

        if (list.isEmpty()) {
            return list;
        }

        LinkedList<T> linkedList = (LinkedList<T>) list;
        int size = linkedList.size();
        int shift = positions % size;

        if (shift < 0) {
            shift += size;
        }

        for (int i = 0; i < shift-1; i++) {
            T last = linkedList.removeLast();
            linkedList.addFirst(last);
        }

        return list;
    }
}