package ua.dp.skillsup;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Andrey Lomakin <a href="mailto:lomakin.andrey@gmail.com">Andrey Lomakin</a>
 * @since 03/11/14
 */
public class FastCounter implements Counter {

    private final AtomicLong[] counters;
    private final int mask;

    private ThreadLocal<Integer> threadLocalHash;

    public FastCounter(int capacity) {
        int adjustedCapacity = findNextPositivePowerOfTwo(capacity);
        mask = adjustedCapacity - 1;
        counters = new AtomicLong[adjustedCapacity];
        for (int index = 0; index < adjustedCapacity; index++) {
            counters[index] = new AtomicLong();
        }
    }

	@Override
	public void inc() {
        if (threadLocalHash == null) {
            threadLocalHash = new ThreadLocal<Integer>();
        }
		int index = threadLocalHash.get() & mask;
        counters[index].incrementAndGet();
	}

	@Override
	public long get() {
        long sum = 0;
		for (AtomicLong counter : counters) {
            sum += counter.get();
        }
        return sum;
	}

    private static int findNextPositivePowerOfTwo(final int value) {
        return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
    }
}