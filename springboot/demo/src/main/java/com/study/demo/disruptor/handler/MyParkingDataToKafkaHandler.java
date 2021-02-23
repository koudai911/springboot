package com.study.demo.disruptor.handler;
import com.lmax.disruptor.EventHandler;
import com.study.demo.disruptor.event.MyInParkingDataEvent;

/**
 * @Description: 第二个消费者，负责发送通知告知工作人员(Kafka是一种高吞吐量的分布式发布订阅消息系统)
 * @Author: luoshangcai
 * @Date 2021-02-19 14:56
 **/
public class MyParkingDataToKafkaHandler implements EventHandler<MyInParkingDataEvent> {

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long l, boolean b) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getCarLicense();
        System.out.println(String.format("Thread Id %s 给  %s 的车主发送一条短信，并告知他计费开始了 ....", threadId, carLicense));
    }
}
