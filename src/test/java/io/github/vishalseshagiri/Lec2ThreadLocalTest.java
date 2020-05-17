package io.github.vishalseshagiri;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class Lec2ThreadLocalTest {

	@Test
	public void test_UseCase1() throws InterruptedException, ExecutionException {
		Lec2ThreadLocal obj = new Lec2ThreadLocal();
		List<Future<String>> listOfCompletedFutures = obj.executeUseCase1();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (int id = 1; id <= 1000; id++) {

			Assert.assertTrue(listOfCompletedFutures.get(id-1).get()
								  .equals(df.format(obj.dbDateString.get(id))));
		}
	}

	@Test
	public void test_UseCase2() throws InterruptedException, ExecutionException {
		Lec2ThreadLocal obj = new Lec2ThreadLocal();
		List<Future<String>> listOfCompletedFutures = obj.executeUseCase2();
		for (int id = 0; id < obj.countryDb.size(); id++) {
			Assert.assertTrue(listOfCompletedFutures.get(id).get().equals(obj.countryDb.get(id).toString()));
		}
	}

}