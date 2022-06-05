package com.examples.distributeLock;

public class Test {
    private static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        JedisLock lock = new JedisLock();
        Thread t1 = new Thread(()->{
            for(int i=0;i<1000;i++){
                lock.lock();
                num++;
                lock.unlock();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i=0;i<1000;i++){
                lock.lock();
                num--;
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(num);
    }
}
