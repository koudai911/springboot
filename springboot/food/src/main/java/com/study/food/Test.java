package com.study.food;

import com.study.food.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-09-18 11:16
 **/
public class Test {
    public static void main(String[] args) {
        List<Food> list =new ArrayList<>();
        System.out.println(System.getProperties());
        int i=0;
        while (true){
            i++;
            list.add(new Food(i,1));
        }
    }
}
