package com.study.food;

import com.study.food.model.Food;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest(classes = FoodApplication.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public  class FoodApplicationTests {

//    @Autowired
//    private FoodMapper foodMapper;

//    @Autowired
//    private FoodTypeMapper foodTypeMapper;

    @Test
    public void contextLoads() {
        String foodType="鲁菜、粤菜、川菜、湘菜、闽菜、浙菜、苏菜、徽菜、家常菜";

        String one ="鸭黄豆腐 李鸿章杂烩 茶叶熏鸡 徽式双冬 玉兔海参 花菇田鸡 板栗仔鸡 铁狮子头";

        String [] footTypeArr=one.split(" ");
        List<Food> list= new ArrayList<>();
        for (int i = 0; i < footTypeArr.length; i++) {
            list.add(new Food(8,footTypeArr[i]));
        }


        for (Food type : list) {
//            foodTypeMapper.insert(type);
//            foodMapper.insert(type);
        }
    }
    @Test
    public void test() {
        List<Food> list =new ArrayList<>();
        System.out.println(System.getProperties());
        int i=0;
        while (true){
            i++;
            list.add(new Food(i,1));
       }
    }
}
