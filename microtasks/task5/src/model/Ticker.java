package ru.nsu.neretin.lab1503.model;

class Ticker extends Thread {
    @Override
    public void start() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {

        }
    }
}
