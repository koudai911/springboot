package com.study.demo.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2021-02-19 17:15
 **/
public class ForkJoinExample extends RecursiveTask<Integer> {

    private final int threshold = 3;
    private int first;
    private int last;

    public ForkJoinExample(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <= threshold) {
            // 任务足够小则直接计算
            for (int i = first; i <= last; i++) {
                result += i;
            }
            System.out.println(Thread.currentThread().getName() + "：∑(" + first + "," + last + ") = " + result);
        } else {
            // 拆分成小任务
            int middle = first + (last - first) / 2;
            ForkJoinExample leftTask = new ForkJoinExample(first, middle);
            ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
            System.out.println(Thread.currentThread().getName() + "："
                    + "∑(" + first + "," + last + ") = "
                    + "∑(" + first + "," + middle + ") + "
                    + "∑(" + (middle + 1) + "," + last + ") = "
                    + result);
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinExample example = new ForkJoinExample(1, 10);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future result = forkJoinPool.submit(example);
        System.out.println("result = " + result.get());
    }
}

