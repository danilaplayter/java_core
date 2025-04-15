package ru.mentee.power.collections.base;

import javax.print.DocFlavor;
import java.util.List;
import java.util.ArrayList;

public class ListUtils {

    public static List<String> mergeLists(List<String> list1, List<String> list2) {
            List<String> result = new ArrayList<>();
            if (list1 == null && list2 == null){
                return null;
            }
            if(list1 != null){
                for(String string : list1){
                    if(!result.contains(string) && string != null){
                        result.add(string);
                    }
                }
            }

            if(list2 != null){
                for(String string : list2){
                    if(!result.contains(string) && string != null){
                        result.add(string);
                    }
                }
            }
            return result;
    }
}