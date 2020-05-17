package io.github.vishalseshagiri.helper;

import java.util.function.Supplier;

public class ThreadSafeFormatter<T> {

	// also note ThreadLocals are generally static variables but need not necessarily be as in this case
	// used non static variable here since I wanted to create a generic ThreadSafeFormatter class
	// https://stackoverflow.com/questions/30134272/can-javas-threadlocal-be-applied-to-a-non-static-field-if-yes-how

	//	public static ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
	//		// called once for each thread
	//		@Override
	//		protected SimpleDateFormat initialValue() {
	//			return new SimpleDateFormat("yyyy-MM-dd");
	//		}
	//
	//		// 1st call = initialValue()
	//		// subsequent calls will return same initialized value
	//		@Override
	//		public SimpleDateFormat get() {
	//			return super.get();
	//		}
	//	};

	// above code reduced to this due to java 8 lambdas

	private Supplier<T> supplier;

	public ThreadSafeFormatter(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	public ThreadLocal<T> holder = ThreadLocal.withInitial(() -> this.supplier.get());

}
