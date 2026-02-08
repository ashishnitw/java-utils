package io.github.ashishnitw.observability;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.core.metrics.Histogram;
import io.prometheus.metrics.model.registry.PrometheusRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    // Prometheus metrics
    private static final ConcurrentHashMap<String, Counter> prometheusCounters = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Gauge> prometheusGauges = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Histogram> prometheusHistograms = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MetricsUtils.class);

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
     * Creates or gets a Prometheus counter metric.
     *
     * @param name the name of the counter
     * @param help the help text for the counter
     * @return the Prometheus counter
     */
    public static Counter getOrCreatePrometheusCounter(String name, String help) {
        return prometheusCounters.computeIfAbsent(name, k -> {
            try {
                Counter counter = Counter.builder()
                        .name(k)
                        .help(help)
                        .register();
                logger.debug("Created Prometheus counter: {}", k);
                return counter;
            } catch (Exception e) {
                logger.error("Failed to create Prometheus counter: {}", name, e);
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Increments a Prometheus counter metric.
     *
     * @param name the name of the counter
     * @param help the help text for the counter
     * @param amount the amount to increment
     */
    public static void incrementPrometheusCounter(String name, String help, double amount) {
        try {
            Counter counter = getOrCreatePrometheusCounter(name, help);
            counter.inc(amount);
            logger.debug("Incremented Prometheus counter {} by {}", name, amount);
        } catch (Exception e) {
            logger.error("Failed to increment Prometheus counter: {}", name, e);
        }
    }

    /**
     * Increments a Prometheus counter metric by 1.
     *
     * @param name the name of the counter
     * @param help the help text for the counter
     */
    public static void incrementPrometheusCounter(String name, String help) {
        incrementPrometheusCounter(name, help, 1.0);
    }

    /**
     * Creates or gets a Prometheus gauge metric.
     *
     * @param name the name of the gauge
     * @param help the help text for the gauge
     * @return the Prometheus gauge
     */
    public static Gauge getOrCreatePrometheusGauge(String name, String help) {
        return prometheusGauges.computeIfAbsent(name, k -> {
            try {
                Gauge gauge = Gauge.builder()
                        .name(k)
                        .help(help)
                        .register();
                logger.debug("Created Prometheus gauge: {}", k);
                return gauge;
            } catch (Exception e) {
                logger.error("Failed to create Prometheus gauge: {}", name, e);
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sets a Prometheus gauge metric value.
     *
     * @param name the name of the gauge
     * @param help the help text for the gauge
     * @param value the value to set
     */
    public static void setPrometheusGauge(String name, String help, double value) {
        try {
            Gauge gauge = getOrCreatePrometheusGauge(name, help);
            gauge.set(value);
            logger.debug("Set Prometheus gauge {} to {}", name, value);
        } catch (Exception e) {
            logger.error("Failed to set Prometheus gauge: {}", name, e);
        }
    }

    /**
     * Creates or gets a Prometheus histogram metric.
     *
     * @param name the name of the histogram
     * @param help the help text for the histogram
     * @return the Prometheus histogram
     */
    public static Histogram getOrCreatePrometheusHistogram(String name, String help) {
        return prometheusHistograms.computeIfAbsent(name, k -> {
            try {
                Histogram histogram = Histogram.builder()
                        .name(k)
                        .help(help)
                        .register();
                logger.debug("Created Prometheus histogram: {}", k);
                return histogram;
            } catch (Exception e) {
                logger.error("Failed to create Prometheus histogram: {}", name, e);
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Observes a value in a Prometheus histogram metric.
     *
     * @param name the name of the histogram
     * @param help the help text for the histogram
     * @param value the value to observe
     */
    public static void observePrometheusHistogram(String name, String help, double value) {
        try {
            Histogram histogram = getOrCreatePrometheusHistogram(name, help);
            histogram.observe(value);
            logger.debug("Observed value {} in Prometheus histogram {}", value, name);
        } catch (Exception e) {
            logger.error("Failed to observe Prometheus histogram: {}", name, e);
        }
    }

    /**
     * Pushes in-memory metrics to Prometheus registry.
     * This method is useful for synchronizing local metrics with Prometheus.
     */
    public static void syncMetricsToPrometheus() {
        try {
            counters.forEach((name, counter) -> {
                String prometheusName = sanitizeMetricName(name);
                Counter promCounter = getOrCreatePrometheusCounter(prometheusName, "Synced from local metrics");
                long currentValue = counter.get();
                // Increment the Prometheus counter with the current value
                promCounter.inc(currentValue);
                logger.debug("Synced counter {} with value {}", prometheusName, currentValue);
            });
        } catch (Exception e) {
            logger.error("Failed to sync metrics to Prometheus", e);
        }
    }

    /**
     * Sanitizes a metric name to be Prometheus-compliant.
     * Replaces non-alphanumeric characters with underscores.
     *
     * @param name the original metric name
     * @return the sanitized metric name
     */
    public static String sanitizeMetricName(String name) {
        if (name == null || name.isEmpty()) {
            return "metric";
        }
        // Prometheus metric names must match: [a-zA-Z_:][a-zA-Z0-9_:]*
        String sanitized = name.replaceAll("[^a-zA-Z0-9_:]", "_");
        // Ensure it starts with a letter or underscore
        if (!sanitized.matches("^[a-zA-Z_].*")) {
            sanitized = "_" + sanitized;
        }
        return sanitized;
    }

    /**
     * Gets the Prometheus registry.
     *
     * @return the PrometheusRegistry instance
     */
    public static PrometheusRegistry getPrometheusRegistry() {
        return PrometheusRegistry.defaultRegistry;
    }

    /**
     * Clears all Prometheus metrics from the registry.
     */
    public static void clearPrometheusMetrics() {
        try {
            prometheusCounters.clear();
            prometheusGauges.clear();
            prometheusHistograms.clear();
            logger.info("Cleared all Prometheus metrics");
        } catch (Exception e) {
            logger.error("Failed to clear Prometheus metrics", e);
        }
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
