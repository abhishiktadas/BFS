package Assignment;

import java.util.LinkedList;
import java.util.Queue;


//
//Create a shared queue with a fixed size.
//A Producer thread adds items to the queue.
//A Consumer thread removes items from the queue.
//Use wait() and notify() to manage communication between threads.


class SharedQueue {
    private final int maxSize;
    private final Queue<Integer> queue = new LinkedList<>();

    public SharedQueue(int size) {
        this.maxSize = size;
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == maxSize) {
            wait();
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notifyAll();
        return item;
    }
}

class Producer implements Runnable {
    private final SharedQueue sharedQueue;

    public Producer(SharedQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedQueue.produce(i);
                Thread.sleep(100); // Simulate time taken to produce an item
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private final SharedQueue sharedQueue;

    public Consumer(SharedQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedQueue.consume();
                Thread.sleep(150); // Simulate time taken to consume an item
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Assign_3 {
    public static void main(String[] args) {
        SharedQueue sharedQueue = new SharedQueue(5);

        Thread producerThread = new Thread(new Producer(sharedQueue));
        Thread consumerThread = new Thread(new Consumer(sharedQueue));

        producerThread.start();
        consumerThread.start();
    }
}
