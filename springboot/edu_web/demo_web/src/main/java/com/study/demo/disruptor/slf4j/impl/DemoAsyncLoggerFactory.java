package com.study.demo.disruptor.slf4j.impl;

import com.lmax.disruptor.dsl.Disruptor;
import com.study.demo.disruptor.slf4j.Level;
import com.study.demo.disruptor.slf4j.LogEvent;
import com.study.demo.disruptor.slf4j.LogEventHandler;
import com.study.demo.disruptor.slf4j.LogThreadFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Logger的单例工厂，读取日志系统配置，并对日志落盘行为进行统一管理
 */
public enum DemoAsyncLoggerFactory implements ILoggerFactory {

    /**
     * 工厂单例
     */
    INSTANCE;

    /**
     * 异步落盘线程的执行队列，使用了无锁内存队列进行日志事件的管理
     */
    private final Disruptor<LogEvent> disruptor;

    /**
     * 日志等级
     */
    private volatile Level level = Level.DEBUG;

    /**
     * 创建一个日志工厂单例，该工厂将统一
     */
    DemoAsyncLoggerFactory() {
        try {
            // 这里为一个DEMO, 正式实现中会定义完整读取配置的方式
            String fileUrl = String.format(System.getProperty("user.dir") + "/tmp/%s.log", UUID.randomUUID().toString());
            // 指定路径如果没有则创建并添加
            File file = new File(fileUrl);
            //获取父目录
            File fileParent = file.getParentFile();
            //判断是否存在
            if (!fileParent.exists()) {
                // 创建父目录文件
                fileParent.mkdirs();
            }
            file.createNewFile();
            disruptor = new Disruptor<>(LogEvent::new, 1024, new LogThreadFactory(fileUrl));
            disruptor.handleEventsWith(new LogEventHandler(
                    new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileUrl)))));
            disruptor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 Logger 实例(Logger工厂方法)
     *
     * @param name 日志名称
     * @return Logger 实例
     */
    @Override
    public Logger getLogger(String name) {
        return new DemoAsyncLogger(name, disruptor);
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return this.level;
    }

}
