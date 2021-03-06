package com.study.demo.disruptor.event;

import lombok.Data;

/**
 * @Description: 汽车信息
 * @Author: luoshangcai
 * @Date 2021-02-19 14:52
 **/
@Data
public class MyInParkingDataEvent {

    /**
     * 车牌号
     **/
    private String carLicense;
}
