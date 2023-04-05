package model;

import java.util.concurrent.locks.ReentrantLock;

public class Printer implements Runnable{
    private final int n;
    private static volatile int k = 0;
    private final ReentrantLock lock;
    public Printer(int n, ReentrantLock lock){
        this.n = n;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(getRandomNumber(1, 2));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(n != k){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
               break;
            }
        }


        lock.lock();
        System.out.println(n+1);
        k++;
        lock.unlock();
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
