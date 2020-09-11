package com.devil.sell.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.swing.text.Segment;
import javax.transaction.UserTransaction;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.Buffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hanyanjiao
 * @date 2020/8/14
 */
public class Test1  {
    public static void main(String[] args) {

        int N = 8;
        Semaphore semaphore = new Semaphore(5);
        for (int i=0;i<N;i++){
            new Worker(i,semaphore).start();
        }

        ReentrantLock lock = new ReentrantLock();

        CountDownLatch countDownLatch = new CountDownLatch(4);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        ExecutorService executorService1 = Executors.newCachedThreadPool();

        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        executorService.execute(new Thread());

        ApplicationContext a = new ClassPathXmlApplicationContext();

        ApplicationContext b = new FileSystemXmlApplicationContext();

        ApplicationContext c = new AnnotationConfigApplicationContext();



    }

    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;

        public Worker (int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();

                System.out.println("worker "+this.num+"占用一个机器在生产");

                Thread.sleep(2000);
                System.out.println("worker "+this.num+"释放出机器");

                semaphore.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }






}
