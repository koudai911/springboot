package com.study.demo.disruptor.producer;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.study.demo.disruptor.event.MyInParkingDataEvent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:  生产者，进入停车场的车辆
 * @Author: luoshangcai
 * @Date 2021-02-19 15:04
 **/
public class MyInParkingDataEventPublisher implements Runnable{
    /**
     * 用于监听初始化操作，等初始化执行完毕后，通知主线程继续工作
     **/
    private CountDownLatch countDownLatch;
    private Disruptor<MyInParkingDataEvent> disruptor;

    private static AtomicInteger count=new AtomicInteger(0);

    private int ss=0;
    /**
     * 1,10,100,1000
     **/
    public static final Integer NUM = 200;

    public MyInParkingDataEventPublisher(CountDownLatch countDownLatch,
                                         Disruptor<MyInParkingDataEvent> disruptor) {
        this.countDownLatch = countDownLatch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {

        try {
            for(int i = 0; i < NUM; i ++) {
                MyInParkingDataEventTranslator eventTranslator = new MyInParkingDataEventTranslator(count.getAndIncrement());
                disruptor.publishEvent(eventTranslator);
                // 执行完毕后通知 await()方法
                countDownLatch.countDown();
                // 假设一秒钟进一辆车
//                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.err.println(NUM + "辆车已经全部进入进入停车场！");
        }
    }

}

class MyInParkingDataEventTranslator implements EventTranslator<MyInParkingDataEvent> {

    private int count;

    public MyInParkingDataEventTranslator(int count){
        this.count=count;
    }

    @Override
    public void translateTo(MyInParkingDataEvent myInParkingDataEvent, long sequence) {
        this.generateData(myInParkingDataEvent,count);
    }

    private MyInParkingDataEvent generateData(MyInParkingDataEvent myInParkingDataEvent,Integer count) {
        // 随机生成一个车牌号
        myInParkingDataEvent.setCarLicense("车牌号： 鄂A-" + count);
        System.out.println("Thread Id " + Thread.currentThread().getId() + " 写完一个event");
        return myInParkingDataEvent;
    }

}
