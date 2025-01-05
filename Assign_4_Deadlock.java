package Assignment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Assign_4_Deadlock {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Task1());
        Thread thread2 = new Thread(new Task2());

        thread1.start();
        thread2.start();
    }

    static class Task1 implements Runnable {
        public void run() {
            lock1.lock();
            try {
                System.out.println("Task1 acquired lock1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                lock2.lock();
                try {
                    System.out.println("Task1 acquired lock2");
                } finally {
                    lock2.unlock();
                }
            } finally {
                lock1.unlock();
            }
        }
    }

    static class Task2 implements Runnable {
        public void run() {
            lock2.lock();
            try {
                System.out.println("Task2 acquired lock2");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                lock1.lock();
                try {
                    System.out.println("Task2 acquired lock1");
                } finally {
                    lock1.unlock();
                }
            } finally {
                lock2.unlock();
            }
        }
    }
}
