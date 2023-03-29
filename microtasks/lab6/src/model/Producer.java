package model;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
    private final BlockingQueue<String> storage;
    private final int productID;

    public Producer(BlockingQueue<String> storage, int productID){
        this.storage = storage;
        this.productID = productID;
    }
    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                storage.put("p - " +  Thread.currentThread().getName() + " - " + productID);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
}
