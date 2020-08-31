package com.study.seata;

import com.study.base.util.CopierUtil;
import com.study.seata.model.OriginBean;
import com.study.seata.model.TargetBean;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-18 17:57
 **/
@SpringBootTest
public class BeanCopierTests {

    private static final BeanCopier copier = BeanCopier.create(OriginBean.class, TargetBean.class, false);

    @Test
    public void contextLoads() {
//        preheat();
//        copySingle();
//        copyList(1000 * 1000);
        List<OriginBean> originBeans = new ArrayList<>();
        OriginBean originBean = new OriginBean();
        for (int i = 0; i < 1000; i++) {
            originBeans.add(new OriginBean());
        }
        TargetBean targetBean = CopierUtil.copyProperties(originBean, TargetBean.class);
        out.println(targetBean.toString());

        List<TargetBean> list = CopierUtil.copyObjects(originBeans, TargetBean.class);
        out.println(list.size());

    }

    /**
     * 预热
     */
    public static void preheat() {
        List<OriginBean> originBeans = new ArrayList<>();
        List<TargetBean> targetBeans = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            originBeans.add(new OriginBean());
            targetBeans.add(new TargetBean());
        }
        for (int i = 0; i < 1000; i++) {
            OriginBean originBean = originBeans.get(i);
            TargetBean targetBean = targetBeans.get(i);
            copier.copy(originBean, targetBean, null);
        }
    }
    /**
     * 测试单个对象属性拷贝
     */
    public static void copySingle(){
        OriginBean originBean = new OriginBean();
        TargetBean targetBean = new TargetBean();

        long start = System.nanoTime();
        copier.copy(originBean, targetBean, null);

        long end = System.nanoTime() - start;
        out.println(new BigDecimal(end).divide(new BigDecimal(1000000), 6, RoundingMode.HALF_UP) + "[ms]");
    }

    /**
     * 测试列表对象属性拷贝
     * @param times
     */
    public static void copyList(int times) {
        List<OriginBean> originBeans = new ArrayList<>();
        List<TargetBean> targetBeans = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            originBeans.add(new OriginBean());
            targetBeans.add(new TargetBean());
        }

        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            OriginBean originBean = originBeans.get(i);
            TargetBean targetBean = targetBeans.get(i);
            copier.copy(originBean, targetBean, null);
        }
        long end = System.nanoTime() - start;
        out.println(new BigDecimal(end).divide(new BigDecimal(1000000), 6, RoundingMode.HALF_UP) + "[ms]");
        out.println(new BigDecimal(end/times).divide(new BigDecimal(1000000), 6, RoundingMode.HALF_UP) + "[ms]");

    }
}
