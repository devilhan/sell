package com.devil.sell.controller;

import java.lang.reflect.Method;

/**
 * 死锁
 * @author Hanyanjiao
 * @date 2020/6/18
 */
class Super{
    public Integer getLength(){
        return new Integer(4);
    }
}

public class TestController extends Super {

    //资源1
    private static final Object resource1 = new Object();
    //资源2
    private static final Object resource2 = new Object();

    /*public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {// 先获得resource1
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "死锁测试线程1").start();

        new Thread(() -> {
            synchronized (resource2) {// 先获得resource2
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "死锁测试线程2").start();
    }*/



    public static void main(String[] args) throws ClassNotFoundException {

        new Thread(() -> {
            synchronized (resource1) {// 先获得resource1
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "死锁测试线程1").start();

        new Thread(() -> {
            synchronized (resource1) { // 先获得resource1
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "死锁测试线程2").start();
    }

}
