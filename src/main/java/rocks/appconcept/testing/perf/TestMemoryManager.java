package rocks.appconcept.testing.perf;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ioannis.metaxas on 2015-11-11.
 * Keeps track of the memory usage of a block of code.
 * Used for monitoring the threads block of code as a whole
 */
public class TestMemoryManager {

    private static long TIME_PERIOD = 50;
    private boolean terminate = false;

    ArrayList<Long> memoryUsage = new ArrayList<Long>();

    /**
     * Starts the memory monitoring
     */
    public void startMonitoring() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!terminate) {
                    Runtime rt = Runtime.getRuntime();
                    long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
                    memoryUsage.add(usedMB);
                }
            }
        }, 0, TIME_PERIOD);
    }

    /**
     * Stops the memory monitoring
     */
    public void stopMonitoring() {
        terminate = true;
    }

    /**
     * Returns the maximum heap memory in MB
     *
     * @return the maximum heap memory
     */
    public long getMaxHeapMemory() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }

    /**
     * Returns the memory usage percentage
     *
     * @param memoryUsage
     * @return the memory usage percentage
     */
    public double getMemoryUsagePerc(long memoryUsage) {
        return ((double) memoryUsage / getMaxHeapMemory()) * 100;
    }

    /**
     * Returns a list with the memory usage in MB for every time period
     *
     * @return a list with the memory usage in MB for every time period
     */
    public ArrayList<Long> getMemoryUsage() {
        return memoryUsage;
    }

    /**
     * Returns the time period for polling the JVM for the memory usage
     *
     * @return
     */
    public long getTimePeriod() {
        return TIME_PERIOD;
    }

    /**
     * Sets a new time period for polling the JVM for the memory usage
     *
     * @param timePeriod for polling the JVM for the memory usage
     */
    public void setTimePeriod(long timePeriod) {
        TIME_PERIOD = timePeriod;
    }
}
