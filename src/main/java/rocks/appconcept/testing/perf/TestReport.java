package rocks.appconcept.testing.perf;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Created by ioannis.metaxas on 2015-11-10.
 * A report which counts and presents time metrics about a tread
 *
 * @see <a href="http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking">http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking</a>
 */
public class TestReport {

    private long id;
    private String name;

    private long startWallClockTime;
    private long startSystemTimeNano;
    private long startUserTimeNano;

    private long endWallClockTime;
    private long endSystemTimeNano;
    private long endUserTimeNano;

    public TestReport(long id, String name) {
        this.id = id;
        this.name = name;
        startWallClockTime = System.currentTimeMillis();
        startSystemTimeNano = getSystemTime();
        startUserTimeNano = getUserTime();
    }

    /**
     * Returns the CPU time in nanoseconds.
     * CPU time is user time plus system time. It's the total time spent using a CPU for your application
     *
     * @return the CPU time in nanoseconds
     */
    private long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }

    /**
     * Returns the user time in nanoseconds.
     * User time is the time spent running your application's own code
     *
     * @return the user time in nanoseconds
     */
    private long getUserTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadUserTime() : 0L;
    }

    /**
     * Returns the system time in nanoseconds.
     * System time is the time spent running OS code on behalf of your application (such as for I/O)
     *
     * @return the system time in nanoseconds
     */
    private long getSystemTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? (bean.getCurrentThreadCpuTime() - bean.getCurrentThreadUserTime()) : 0L;
    }

    /**
     * Returns the current wall clock time
     * Wall clock time is the real-world elapsed time experienced by a user waiting for a task to complete
     *
     * @return the current wall clock time
     */
    public long getCurrentWallClockTime() {
        return System.currentTimeMillis() - startWallClockTime;
    }

    /**
     * Returns the current user time in nanoseconds.
     * User time is the time spent running your application's own code
     *
     * @return the current user time in nanoseconds
     */
    public long getCurrentUserTime() {
        return getUserTime() - startUserTimeNano;
    }

    /**
     * Returns the current system time in nanoseconds.
     * System time is the time spent running OS code on behalf of your application (such as for I/O)
     *
     * @return
     */
    public long getCurrentSystemTime() {
        return getSystemTime() - startSystemTimeNano;
    }

    /**
     * Prints all the current gathered information
     */
    public void printInfo() {
        System.out.println("\n" + name + "#" + id);
        System.out.println("Wall clock time: " + endWallClockTime + " ms ~ " + convertFromMillisToSec(endWallClockTime) + " sec");
        System.out.println("User time: " + endUserTimeNano + " ns ~ " + convertFromNanoToSec(endUserTimeNano) + " sec");
        System.out.println("System time: " + endSystemTimeNano + " ns ~ " + convertFromNanoToSec(endSystemTimeNano) + " sec");
        System.out.println("CPU time: " + (endUserTimeNano + endSystemTimeNano) + " ns ~ " + convertFromNanoToSec(endUserTimeNano + endSystemTimeNano) + " sec\n");
    }

    /**
     * Stops the timers
     */
    public void stopTimers() {
        endWallClockTime = System.currentTimeMillis() - startWallClockTime;
        endUserTimeNano = getUserTime() - startUserTimeNano;
        endSystemTimeNano = getSystemTime() - startSystemTimeNano;
    }

    /**
     * Resets the timers
     */
    public void resetTimers() {
        startWallClockTime = System.currentTimeMillis();
        startSystemTimeNano = getSystemTime();
        startUserTimeNano = getUserTime();
    }

    /**
     * Converts and returns the time from nanosec to seconds
     *
     * @param time in nanosecs
     * @return the time from nanosec to seconds
     */
    public static double convertFromNanoToSec(long time) {
        return ((double) time / 1000000000);
    }

    /**
     * Converts and returns the time from millisec to seconds
     *
     * @param time
     * @return the time from millisec to seconds
     */
    public static double convertFromMillisToSec(long time) {
        return ((double) time / 1000);
    }

    /**
     * Returns the end user time in nanosec which is the time after calling the stopTimers() method
     * User time is the time spent running your application's own code
     *
     * @return the end user time in nanosec
     */
    public long getEndUserTimeNano() {
        return endUserTimeNano;
    }

    /**
     * Returns the end system time in nanosec which is the time after calling the stopTimers() method
     * System time is the time spent running OS code on behalf of your application (such as for I/O)
     *
     * @return the end system time in nanosec
     */
    public long getEndSystemTimeNano() {
        return endSystemTimeNano;
    }

    /**
     * Returns the end wall clock time in millisec which is the time after calling the stopTimers() method
     * Wall clock time is the real-world elapsed time experienced by a user waiting for a task to complete
     *
     * @return the end system time in nanosec
     */
    public long getEndWallClockTime() {
        return endWallClockTime;
    }
}
