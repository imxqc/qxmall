package com.cqx.qxmall.search.thread;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/28 14:36
 */
public class ThreadTest {
    //自定义线程池service
    public static ExecutorService service = Executors.newFixedThreadPool(10);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "im task1";
        }, service);

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "im task2";
        }, service);

        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            return "im task3";
        }, service);

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(task1, task2, task3);

        System.out.println("三个任务中最先完成的任务 = " + anyOf.get());

        System.out.println("全部完成" + task1.get() + task3.get() + task2.get());

    }


}
