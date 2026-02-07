package io.github.ashishnitw.observability;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for metrics and monitoring operations.
 * This class provides static methods for collecting and managing application metrics,
 * including counters, timers, and custom measurements.
 *
 * @author ashishnitw
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MetricsUtils {

    private static final ConcurrentHashMap<String, AtomicLong> counters = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> timers = new ConcurrentHashMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private MetricsUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Increments a counter by 1.
     *
     * @param counterName the name of the counter
     * @return the new counter value
     */
    public static long incrementCounter(String counterName) {
        return incrementCounter(counterName, 1);
    }

    /**
     * Increments a counter by the specified amount.
     *
     * @param counterName the name of the counter
     * @param amount the amount to increment
     * @return the new counter value
     */
    public static long incrementCounter(String counterName, long amount) {
        return counters.computeIfAbsent(counterName, k -> new AtomicLong(0))
                .addAndGet(amount);
    }

    /**
     * Gets the current value of a counter.
     *
     * @param counterName the name of the counter
     * @return the counter value, or 0 if the counter doesn't exist
     */
    public static long getCounterValue(String counterName) {
        AtomicLong counter = counters.get(counterName);
        return counter != null ? counter.get() : 0L;
    }

    /**
     * Resets a counter to 0.
     *
     * @param counterName the name of the counter
     */
    public static void resetCounter(String counterName) {
        AtomicLong counter = counters.get(counterName);
        if (counter != null) {
            counter.set(0);
        }
    }

    /**
     * Starts a timer by recording the current system time.
     *
     * @param timerName the name of the timer
     */
    public static void startTimer(String timerName) {
        timers.put(timerName, System.currentTimeMillis());
    }

    /**
     * Stops a timer and returns the elapsed time in milliseconds.
     *
     * @param timerName the name of the timer
     * @return the elapsed time in milliseconds, or -1 if the timer doesn't exist
     */
    public static long stopTimer(String timerName) {
        Long startTime = timers.remove(timerName);
        if (startTime == null) {
            return -1L;
        }
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Gets the elapsed time for an active timer without stopping it.
     *
     * @param timerName the name of the timer
     * @return the elapsed time in milliseconds, or -1 if the timer doesn't exist
     */
    public static long getElapsedTime(String timerName) {
        Long startTime = timers.get(timerName);
        if (startTime == null) {
            return -1L;
        }
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Measures the execution time of a code block.
     *
     * @param <T> the return type of the code block
     * @param operationName the name of the operation for logging purposes
     * @param operation the code block to execute and measure
     * @return the result of the operation
     * @throws Exception if the operation throws an exception
     */
    public static <T> T measureOperation(String operationName, MeasurableOperation<T> operation) throws Exception {
        long startTime = System.currentTimeMillis();
        try {
            return operation.execute();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            incrementCounter(operationName + "_count");
            incrementCounter(operationName + "_totalTime", duration);
        }
    }

    /**
     * Gets memory statistics (used, max, free memory in bytes).
     *
     * @return an array [used, max, free]
     */
    public static long[] getMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();
        long max = runtime.maxMemory();
        long free = runtime.freeMemory();
        return new long[]{used, max, free};
    }

    /**
     * Gets CPU count.
     *
     * @return the number of available processors
     */
    public static int getProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Functional interface for measurable operations.
     *
     * @param <T> the return type
     */
    @FunctionalInterface
    public interface MeasurableOperation<T> {
        /**
         * Executes the operation.
         *
         * @return the result
         * @throws Exception if the operation fails
         */
        T execute() throws Exception;
    }
}
