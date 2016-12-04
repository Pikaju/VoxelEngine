package org.pikaju.game.threads;

import java.util.ArrayList;

public class ThreadManager implements Runnable {

	private static int MAX_THREAD_COUNT = 6;

	private static ArrayList<Thread> runningThreads;
	private static boolean running = false;

	public static void start() {
		runningThreads = new ArrayList<Thread>();
		running = true;
		new Thread(new ThreadManager(), "Thread Manager").start();
	}

	public static void stop() {
		running = false;
	}

	public static void requestThread(Runnable runnable, String name) {
		while (MAX_THREAD_COUNT != -1 && runningThreads.size() >= MAX_THREAD_COUNT) {
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Thread thread = new Thread(runnable, name);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
		runningThreads.add(thread);
	}

	public void run() {
		while (running) {
			for (int i = 0; i < runningThreads.size(); i++) {
				if (runningThreads.get(i) == null || !runningThreads.get(i).isAlive()) {
					runningThreads.remove(i);
					i--;
				}
			}
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
