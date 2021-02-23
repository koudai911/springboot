package com.study.demo.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.study.demo.disruptor.event.MyInParkingDataEvent;

/**
 * @Description: Handler 第一个消费者，负责保存进场汽车的信息
 * @Author: luoshangcai
 * @Date 2021-02-19 14:53
 **/
public class MyParkingDataInDbHandler implements EventHandler<MyInParkingDataEvent>, WorkHandler<MyInParkingDataEvent> {

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getCarLicense();
        System.out.println(String.format("Thread Id %s 保存 %s 到数据库中 ....", threadId, carLicense));
    }

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long sequence, boolean endOfBatch)
            throws Exception {
        this.onEvent(myInParkingDataEvent);
    }
}
