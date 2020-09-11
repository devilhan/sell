package com.devil.sell.controller;



import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Hanyanjiao
 * @date 2020/8/4
 */

public class Test extends Thread {

    private String testMethod(String value){
        return "the value is "+value;
    }

    public void copyFile(String oldPath,String newPath) throws IOException {
       FileWriter fw = new FileWriter(newPath);
       FileInputStream fis = new FileInputStream(oldPath);
       InputStreamReader isr = new InputStreamReader(fis,"utf-8");
       BufferedReader br = new BufferedReader(isr);
       BufferedWriter bw = new BufferedWriter(fw);

       while (br.readLine()!=null){
           bw.write(br.readLine());
           bw.newLine();
       }
       bw.close();
    }


    public static void main(String[] args){
        Test test = new Test();
//        test.localCut();
//        test.localCopy();
        System.out.println(4&7);

    }

    private void localCopy(){
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(new File("D:/log/1.txt"));
            fo = new FileOutputStream(new File("D:/log/test/2.txt"));
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0,in.size(),out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fi.close();
                fo.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void localCut() throws FileNotFoundException {
        File file = new File("D:/feedback");
        File[] files = file.listFiles();
        for (File file1 : files) {
            String fileName = file1.getName();
            int index = fileName.indexOf(".");
            String firstName = fileName.substring(0,index);
            String lastName = fileName.substring(index);
            if (lastName.equals(".java")){
                boolean result = file1.renameTo(new File("D:/expression/" + firstName + ".jpg"));
                System.out.println(result);
            }
        }
    }

}
