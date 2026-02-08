package io.github.ashishnitw.observability;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MetricsUtils.
 */
class MetricsUtilsTest {

    @BeforeEach
    void setUp() {
        // Reset counters for each test
        MetricsUtils.resetCounter("test_counter");
        MetricsUtils.resetCounter("test_op_count");
        MetricsUtils.resetCounter("test_op_totalTime");
    }

    @Test
    void testIncrementCounter_ByOne() {
        long value = MetricsUtils.incrementCounter("test_counter");
        assertEquals(1, value);
        
        value = MetricsUtils.incrementCounter("test_counter");
        assertEquals(2, value);
    }

    @Test
    void testIncrementCounter_ByAmount() {
        long value = MetricsUtils.incrementCounter("test_counter", 5);
        assertEquals(5, value);
        
        value = MetricsUtils.incrementCounter("test_counter", 3);
        assertEquals(8, value);
    }

    @Test
    void testGetCounterValue() {
        MetricsUtils.incrementCounter("test_counter", 10);
        long value = MetricsUtils.getCounterValue("test_counter");
        assertEquals(10, value);
    }

    @Test
    void testGetCounterValue_NonExistent() {
        long value = MetricsUtils.getCounterValue("nonexistent_counter");
        assertEquals(0, value);
    }

    @Test
    void testResetCounter() {
        MetricsUtils.incrementCounter("test_counter", 10);
        MetricsUtils.resetCounter("test_counter");
        long value = MetricsUtils.getCounterValue("test_counter");
        assertEquals(0, value);
    }

    @Test
    void testStartAndStopTimer() throws InterruptedException {
        MetricsUtils.startTimer("test_timer");
        Thread.sleep(100);
        long elapsed = MetricsUtils.stopTimer("test_timer");
        
        assertTrue(elapsed >= 100, "Elapsed time should be at least 100ms");
    }

    @Test
    void testStopTimer_NonExistent() {
        long elapsed = MetricsUtils.stopTimer("nonexistent_timer");
        assertEquals(-1, elapsed);
    }

    @Test
    void testGetElapsedTime() throws InterruptedException {
        MetricsUtils.startTimer("test_timer");
        Thread.sleep(50);
        long elapsed = MetricsUtils.getElapsedTime("test_timer");
        
        assertTrue(elapsed >= 50, "Elapsed time should be at least 50ms");
        
        // Timer should still be active
        long elapsed2 = MetricsUtils.getElapsedTime("test_timer");
        assertTrue(elapsed2 >= elapsed);
    }

    @Test
    void testGetElapsedTime_NonExistent() {
        long elapsed = MetricsUtils.getElapsedTime("nonexistent_timer");
        assertEquals(-1, elapsed);
    }

    @Test
    void testMeasureOperation_Success() throws Exception {
        long result = MetricsUtils.measureOperation("test_op", () -> {
            Thread.sleep(50);
            return 42L;
        });
        
        assertEquals(42L, result);
        long count = MetricsUtils.getCounterValue("test_op_count");
        assertEquals(1, count);
        long totalTime = MetricsUtils.getCounterValue("test_op_totalTime");
        assertTrue(totalTime >= 50);
    }

    @Test
    void testMeasureOperation_Exception() {
        Exception testException = new RuntimeException("test error");
        
        assertThrows(RuntimeException.class, () -> {
            MetricsUtils.measureOperation("test_op", () -> {
                throw testException;
            });
        });
        
        // Counter should still be incremented even on exception
        long count = MetricsUtils.getCounterValue("test_op_count");
        assertEquals(1, count);
    }

    @Test
    void testGetMemoryStats() {
        long[] stats = MetricsUtils.getMemoryStats();
        
        assertEquals(3, stats.length);
        assertTrue(stats[0] > 0, "Used memory should be > 0");
        assertTrue(stats[1] > 0, "Max memory should be > 0");
        assertTrue(stats[2] > 0, "Free memory should be > 0");
        assertTrue(stats[0] <= stats[1], "Used memory should be <= max memory");
    }

    @Test
    void testGetProcessorCount() {
        int processors = MetricsUtils.getProcessorCount();
        assertTrue(processors > 0, "Processor count should be > 0");
    }

    @Test
    void testUtilityClassCannotBeInstantiated() {
        try {
            var constructor = MetricsUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
            assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        }
    }

    // Prometheus Tests

    @Test
    void testGetOrCreatePrometheusCounter() {
        var counter = MetricsUtils.getOrCreatePrometheusCounter("test_prom_counter", "Test counter");
        assertNotNull(counter);
    }

    @Test
    void testIncrementPrometheusCounter() {
        MetricsUtils.incrementPrometheusCounter("prom_counter1", "Prometheus counter 1", 5.0);
        MetricsUtils.incrementPrometheusCounter("prom_counter1", "Prometheus counter 1", 3.0);
        
        var counter = MetricsUtils.getOrCreatePrometheusCounter("prom_counter1", "Prometheus counter 1");
        assertNotNull(counter);
    }

    @Test
    void testIncrementPrometheusCounter_DefaultAmount() {
        MetricsUtils.incrementPrometheusCounter("prom_counter2", "Prometheus counter 2");
        MetricsUtils.incrementPrometheusCounter("prom_counter2", "Prometheus counter 2");
        
        var counter = MetricsUtils.getOrCreatePrometheusCounter("prom_counter2", "Prometheus counter 2");
        assertNotNull(counter);
    }

    @Test
    void testGetOrCreatePrometheusGauge() {
        var gauge = MetricsUtils.getOrCreatePrometheusGauge("test_prom_gauge", "Test gauge");
        assertNotNull(gauge);
    }

    @Test
    void testSetPrometheusGauge() {
        MetricsUtils.setPrometheusGauge("prom_gauge1", "Prometheus gauge 1", 42.0);
        MetricsUtils.setPrometheusGauge("prom_gauge1", "Prometheus gauge 1", 50.0);
        
        var gauge = MetricsUtils.getOrCreatePrometheusGauge("prom_gauge1", "Prometheus gauge 1");
        assertNotNull(gauge);
    }

    @Test
    void testGetOrCreatePrometheusHistogram() {
        var histogram = MetricsUtils.getOrCreatePrometheusHistogram("test_prom_histogram", "Test histogram");
        assertNotNull(histogram);
    }

    @Test
    void testObservePrometheusHistogram() {
        MetricsUtils.observePrometheusHistogram("prom_histogram1", "Prometheus histogram 1", 10.0);
        MetricsUtils.observePrometheusHistogram("prom_histogram1", "Prometheus histogram 1", 20.0);
        MetricsUtils.observePrometheusHistogram("prom_histogram1", "Prometheus histogram 1", 30.0);
        
        var histogram = MetricsUtils.getOrCreatePrometheusHistogram("prom_histogram1", "Prometheus histogram 1");
        assertNotNull(histogram);
    }

    @Test
    void testSanitizeMetricName() {
        assertEquals("test_metric", MetricsUtils.sanitizeMetricName("test-metric"));
        assertEquals("test_metric_123", MetricsUtils.sanitizeMetricName("test.metric.123"));
        assertEquals("_123metric", MetricsUtils.sanitizeMetricName("123metric"));
        assertEquals("test_metric_", MetricsUtils.sanitizeMetricName("test@metric!"));
        assertEquals("metric", MetricsUtils.sanitizeMetricName(""));
    }

    @Test
    void testGetPrometheusRegistry() {
        var registry = MetricsUtils.getPrometheusRegistry();
        assertNotNull(registry);
    }

    @Test
    void testClearPrometheusMetrics() {
        MetricsUtils.incrementPrometheusCounter("prom_counter3", "Prometheus counter 3");
        MetricsUtils.setPrometheusGauge("prom_gauge2", "Prometheus gauge 2", 100.0);
        
        MetricsUtils.clearPrometheusMetrics();
        
        // Verify metrics are cleared by checking that we can create new ones
        var newCounter = MetricsUtils.getOrCreatePrometheusCounter("prom_counter4", "New counter");
        assertNotNull(newCounter);
    }
}
