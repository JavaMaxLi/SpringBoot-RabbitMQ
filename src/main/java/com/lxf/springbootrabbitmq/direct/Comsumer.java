package com.lxf.springbootrabbitmq.direct;

import java.util.concurrent.*;

public class Comsumer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,8,1000, TimeUnit.SECONDS,new ArrayBlockingQueue(10), new ThreadPoolExecutor.CallerRunsPolicy());
        String queueName = "queue1";
        try{
            Future<String> order_01 = executor.submit(new ConsumerCallable("queue_order_01"));
            Future<String> order_02 = executor.submit(new ConsumerCallable("queue_order_02"));
            Future<String> order_03 = executor.submit(new ConsumerCallable("queue_order_03"));


            System.out.println("queue_order_01"+order_01.get());
            System.out.println("queue_order_02"+order_02.get());
            System.out.println("queue_order_03"+order_03.get());
        } catch (Exception e) {

        } finally {
            executor.shutdown();
        }
    }
}
