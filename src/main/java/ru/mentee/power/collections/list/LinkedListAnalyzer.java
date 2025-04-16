package ru.mentee.power.collections.list;

import java.util.*;

public class LinkedListAnalyzer {

    public static <T> List<T> mergeListsPreserveOrder(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Один из списков list1 или list2 - null");
        }

        Set<T> seen = new LinkedHashSet<>();
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
        if (list == null) {
            throw new IllegalArgumentException("Список null");
        }

        if (list.size() <= 1) {
            return new LinkedList<>(list);
        }

        LinkedList<T> reversed = new LinkedList<>();
        for (T element : list) {
            reversed.addFirst(element);
        }

        return reversed;
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список null");
        }

        Set<T> seen = new LinkedHashSet<>();
        List<T> result = new LinkedList<>();

        for (T item : list) {
            if (seen.add(item)) {
                result.add(item);
            }
        }

        return result;
    }

    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list == null) {
            throw new IllegalArgumentException("Список null");
        }

        if (list.isEmpty()) {
            return new LinkedList<>(list);
        }

        LinkedList<T> result = new LinkedList<>(list);
        int size = result.size();
        int shift = positions % size;

        if (shift < 0) {
            shift += size;
        }

        for (int i = 0; i < shift; i++) {
            T last = result.removeLast();
            result.addFirst(last);
        }

        return result;
    }
}