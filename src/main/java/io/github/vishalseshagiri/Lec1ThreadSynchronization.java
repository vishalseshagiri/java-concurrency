package io.github.vishalseshagiri;

import io.github.vishalseshagiri.helper.CustomRunner;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec1ThreadSynchronization {
	public volatile static int value = 0;
	public static AtomicInteger atomicIntegerValue = new AtomicInteger(0);
	private static final Object valueLock = new Object();

	protected int runWithoutSynchronizedCodeBlock() throws InterruptedException {
		Lec1ThreadSynchronization.value = 0;
		CustomRunner thread1 = new CustomRunner("thread1", (CustomRunner c) -> {
			for(int i=0; i < 10000; i++) {
				Lec1ThreadSynchronization.value++;
			};
		});
		CustomRunner thread2 = new CustomRunner("thread2", (CustomRunner c) -> {
			for(int i=0; i < 10000; i++) {
				Lec1ThreadSynchronization.value++;
			};
		});
		thread1.start();
		thread2.start();
		thread1.joinThread();
		thread2.joinThread();
		return value;
	}

	protected int runWithSynchronizedCodeBlock() throws InterruptedException {
		Lec1ThreadSynchronization.value = 0;
		CustomRunner thread1 = new CustomRunner("thread1", (CustomRunner c) -> {
			for(int i=0; i < 10000; i++) {
				synchronized (valueLock) {
					value++;
				}
			};
		});
		CustomRunner thread2 = new CustomRunner("thread2", (CustomRunner c) -> {
			for(int i=0; i < 10000; i++) {
				synchronized (valueLock) {
					value++;
				}
			};
		});
		thread1.start();
		thread2.start();
		thread1.joinThread();
		thread2.joinThread();
		return value;
	}

	protected int runWithAtomicIntegerIncrementInCodeBlock() throws InterruptedException {
		CustomRunner thread1 = new CustomRunner("thread1", (CustomRunner c) -> {
			for(int i=0; i < 10000; i++) {
				atomicIntegerValue.getAndIncrement();
			};
		});
		CustomRunner thread2 = new CustomRunner("thread2", (CustomRunner c) -> {
			for(int i=0; i < 10000; i++) {
				atomicIntegerValue.getAndIncrement();
			};
		});
		thread1.start();
		thread2.start();
		thread1.joinThread();
		thread2.joinThread();
		return atomicIntegerValue.get();
	}

}
