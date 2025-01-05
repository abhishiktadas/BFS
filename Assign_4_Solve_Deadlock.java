package Assignment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Assign_4_Solve_Deadlock 
{
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
            while (true) {
                try {
                    if (lock1.tryLock(50, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Task1 acquired lock1");
                            if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("Task1 acquired lock2");
                                    break; // Both locks acquired, exit loop
                                } finally {
                                    lock2.unlock();
                                }
                            }
                        } finally {
                            lock1.unlock();
                        }
                    }
                    // Sleep before retrying
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Task2 implements Runnable {
        public void run() {
            while (true) {
                try {
                    if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Task2 acquired lock2");
                            if (lock1.tryLock(50, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("Task2 acquired lock1");
                                    break; // Both locks acquired, exit loop
                                } finally {
                                    lock1.unlock();
                                }
                            }
                        } finally {
                            lock2.unlock();
                        }
                    }
                    // Sleep before retrying
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

