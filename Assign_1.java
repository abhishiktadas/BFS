package Assignment;

public class Assign_1 {
	    public static void main(String[] args) {
	        // Create the first thread to print numbers from 1 to 10
	        Thread thread1 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                for (int i = 1; i <= 10; i++) {
	                    System.out.println(i);
	                }
	            }
	        });

	        // Create the second thread to print numbers from 11 to 20
	        Thread thread2 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                for (int i = 11; i <= 20; i++) {
	                    System.out.println(i);
	                }
	            }
	        });

	        // Start both threads
	        thread1.start();
	        thread2.start();
	    }
	}
