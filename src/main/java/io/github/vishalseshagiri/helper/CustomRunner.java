package io.github.vishalseshagiri.helper;

import java.util.function.Consumer;

public class CustomRunner implements Runnable {

	private Thread t;
	private String threadName;
	private Consumer<CustomRunner> runLogic;

	public CustomRunner(String threadName, Consumer<CustomRunner> runLogic) {
		this.threadName = threadName;
		this.runLogic = runLogic;
	}

	@Override
	public void run() {
		runLogic.accept(this);
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public String getThreadName() {
		return threadName;
	}

	public void interrupt() {
		t.interrupt();
	}

	public boolean getInterruptedStatus() {
		return t.isInterrupted();
	}

	public Thread.State getThreadState() {
		return t.getState();
	}

	public void joinThread() throws InterruptedException {
		t.join();
	}

}
