package io.github.vishalseshagiri;

import io.github.vishalseshagiri.helper.Country;
import io.github.vishalseshagiri.helper.ThreadSafeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class Lec2ThreadLocal {

	private static ExecutorService threadPool = Executors.newFixedThreadPool(1000);
	private static ThreadSafeFormatter<Country> countryContextHolder = new ThreadSafeFormatter<>(() -> new Country());
	private static ThreadSafeFormatter<SimpleDateFormat> threadSafeFormatter = new ThreadSafeFormatter<SimpleDateFormat>(() -> {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df;
	});
	// not thread safe
	// private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// using locks will introduce synchronization which will introduce latency in the code

	// https://www.baeldung.com/java-random-dates
	private Date getRandomDateInBoundedInterval(Date startInclusive, Date endInclusive) {
		long startMillis = startInclusive.getTime();
		long endMillis = endInclusive.getTime();
		long randomMillisSinceEpoch = ThreadLocalRandom
			.current()
			.nextLong(startMillis, endMillis);
		return new Date(randomMillisSinceEpoch);
	}

	protected ConcurrentHashMap<Integer, Date> dbDateString = new ConcurrentHashMap<Integer, Date>(){
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				for (int i = 1; i < 2000; i++) {
					put(i, getRandomDateInBoundedInterval(
						dateFormat.parse("2020-01-01"),
						dateFormat.parse("2020-05-16")));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	};

	protected ConcurrentHashMap<Integer, Country> countryDb = new ConcurrentHashMap<Integer, Country>() {
		{
			String[] locales = Locale.getISOCountries();
			for (int i = 0; i < locales.length; i++) {
				Locale obj = new Locale("", locales[i]);
				put(i, new Country(i, obj.getDisplayCountry(), obj.getCountry()));
			}
		}
	};

	// Slightly inefficient since each task has it's own dateformat object
	//	protected String birthDate(int userId) {
	//		Date birthDate = birthDateFromDB(userId);
	//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//		return df.format(birthDate);
	//	}

	protected String birthDate(int userId) {
		Date birthDate = birthDateFromDB(userId);
		final SimpleDateFormat df = threadSafeFormatter.holder.get();
		return df.format(birthDate);
	}

	private Date birthDateFromDB(int userId) {
		return dbDateString.get(userId);
	}


	public List<Future<String>> executeUseCase1() throws InterruptedException {
		// Thread safety and memory efficient
		List<Callable<String>> listOfCallables = new ArrayList<>();
		for (int id = 1; id <= 1000; id++) {
			int finalId = id;
			listOfCallables.add(() -> {
				String birthDate = birthDate(finalId);
				return birthDate;
			});
		};
		return threadPool.invokeAll(listOfCallables);
	}

	private void setCountryContextHolder(Country country) {
		this.countryContextHolder.holder.set(country);
	}

	private String service1(int countryId) {
		Country country = countryDb.get(countryId);
		setCountryContextHolder(country);
		return "Country{" +
			   "countryId=" + countryId + service2();
	}

	private String service2() {
		Country country = countryContextHolder.holder.get();
		return ", countryName='" + country.getCountryName() + '\'' + service3();
	}

	private String service3() {
		Country country = countryContextHolder.holder.get();
		return ", countryCode='" + country.getCountryCode() + '\'' +
			 '}';
	}

	public List<Future<String>> executeUseCase2() throws InterruptedException {
		// Per thread context - thread safety + performance
		List<Callable<String>> listOfCallables = new ArrayList<>();
		for (int id = 0; id < countryDb.size(); id++) {
			int finalId = id;
			listOfCallables.add(() -> {
				String countryToString = new Lec2ThreadLocal().service1(finalId);
				return countryToString;
			});
		};
		return threadPool.invokeAll(listOfCallables);
	}

}
