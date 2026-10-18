package com.itheima;

public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal<>();
    public static void main(String[] args) {
        local.set("Main Message");
        //创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":"+local.get());
            }
        }).start();



        local.remove();
        System.out.println(Thread.currentThread().getName()+":"+local.get());
    }
}
