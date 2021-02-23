package com.study.demo.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.study.demo.disruptor.event.MyInParkingDataEvent;

/**
 * @Description: 第三个消费者，sms短信服务，告知司机你已经进入停车场，计费开始。
 * @Author: luoshangcai
 * @Date 2021-02-19 14:59
 **/
public class MyParkingDataSmsHandler implements EventHandler<MyInParkingDataEvent> {

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long l, boolean b) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getCarLicense();
        System.out.println(String.format("Thread Id %s 发送 %s 进入停车场信息给 kafka系统...", threadId, carLicense));
    }
}
