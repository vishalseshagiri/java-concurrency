package io.github.vishalseshagiri;

import io.github.vishalseshagiri.helper.CustomRunner;
import org.junit.Assert;
import org.junit.Test;

public class Lec1VolatileTest {
	@Test
	public void whenNonVolatileFlag_VisibilityProblemOccurs() throws InterruptedException {
		CustomRunner thread1 = new CustomRunner("thread1", (CustomRunner c) -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Lec1Volatile.nonVolatileFlag = false;
			System.out.println("Thread " + c.getThreadName() + " completed");
		});
		CustomRunner thread2 = new CustomRunner("thread2", (CustomRunner c) -> {
			while (Lec1Volatile.nonVolatileFlag) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("Thread " + c.getThreadName() + "-Interrupted");
					break;
				}
				// do some processing
				System.out.println("Thread " + c.getThreadName() + " Still processing");
			}
			System.out.println("Thread " + c.getThreadName() + " completed");
		});
		thread2.start();
		thread1.start();
		Thread.sleep(10000);
		// This cannot be guaranteed
		// Assert.assertTrue(( thread2.getInterruptedStatus() == false ) && (thread2.getThreadState() == Thread.State.RUNNABLE));
		if (thread2.getThreadState() != Thread.State.TERMINATED) {
			thread2.interrupt();
			Thread.sleep(50);
		}
	}

	@Test
	public void whenVolatileFlag_VisibilityProblemDoesNotOccur() throws InterruptedException {
		CustomRunner thread1 = new CustomRunner("thread1", (CustomRunner c) -> {
			Lec1Volatile.volatileFlag = false;
			System.out.println("Thread " + c.getThreadName() + " completed");
		});
		CustomRunner thread2 = new CustomRunner("thread2", (CustomRunner c) -> {
			while (Lec1Volatile.volatileFlag) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("Thread " + c.getThreadName() + "-Interrupted");
					break;
				}
				// do some processing
				System.out.println("Thread " + c.getThreadName() + " Still processing");
			}
			System.out.println("Thread " + c.getThreadName() + " completed");
		});
		thread2.start();
		thread1.start();
		Thread.sleep(1000);
		Assert.assertTrue(( thread2.getInterruptedStatus() == false ) && (thread2.getThreadState() == Thread.State.TERMINATED));
	}

}