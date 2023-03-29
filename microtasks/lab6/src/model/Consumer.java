package model;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private final BlockingQueue<?> storage;

    public Consumer(BlockingQueue<?> storage) {
        this.storage = storage;
    }
    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                System.out.println("c - " + Thread.currentThread().getName() + " consumes - " + storage.take());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
