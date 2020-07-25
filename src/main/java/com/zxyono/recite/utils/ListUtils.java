package com.zxyono.recite.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {
    public static <T> List<T> shuffle(List<T> list) {
        List<T> tempList = new ArrayList<>();
        list.stream().forEach(item->tempList.add(item));

        int size = tempList.size();
        Random random = new Random();

        for(int i = 0; i < size; i++) {
            // 获取随机位置
            int randomPos = random.nextInt(size);

            // 当前元素与随机元素交换
            T temp = tempList.get(i);
            tempList.set(i, tempList.get(randomPos));
            tempList.set(randomPos, temp);
        }

        return tempList;
    }
}