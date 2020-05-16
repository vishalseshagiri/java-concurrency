package io.github.vishalseshagiri;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class Lec1ThreadSynchronizationTest {

	@Test
	public void testThreadSynchronization() throws InterruptedException {
		int expectedResult = 20000;
		Lec1ThreadSynchronization obj = new Lec1ThreadSynchronization();
		int valueWithoutSynchronizedBlock;
		while(true) {
			valueWithoutSynchronizedBlock = obj.runWithoutSynchronizedCodeBlock();
			if (valueWithoutSynchronizedBlock != expectedResult) {
				break;
			}
		}
		int valueWithSynchronizedCodeBlock = obj.runWithSynchronizedCodeBlock();
		int valueWithAtomicIntegerIncrementInCodeBlock = obj.runWithAtomicIntegerIncrementInCodeBlock();
		Assert.assertNotEquals(valueWithoutSynchronizedBlock, expectedResult);
		Assert.assertEquals(valueWithSynchronizedCodeBlock, expectedResult);
		Assert.assertEquals(valueWithAtomicIntegerIncrementInCodeBlock, expectedResult);
	}
}