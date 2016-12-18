package rocks.appconcept.testing.perf;

import java.util.ArrayList;

/**
 * Created by ioannis.metaxas on 2015-11-11.
 * <p>
 * A thread manager for handling the test threads.
 * It counts and presents the thread time metric summaries and monitors the memory used by each thread.
 */
public class TestThreadManager {

    private ArrayList<SingleTestRunner> threads;

    private long sumWallClockTime;
    private long sumSystemTimeNano;
    private long sumUserTimeNano;

    public TestThreadManager() {
        threads = new ArrayList<>();
    }

    /**
     * Returns the sum of the wall clock time of all the threads in millisec
     * Wall clock time is the real-world elapsed time experienced by a user waiting for a task to complete
     *
     * @return the sum of the wall clock time of all the threads
     */
    public long getSumWallClockTime() {
        sumWallClockTime = 0;
        for (TestReport report : getReports()) {
            sumWallClockTime += report.getEndWallClockTime();
        }
        return sumWallClockTime;
    }

    /**
     * Returns the sum of the user time of all the threads in nanosec
     * User time is the time spent running your application's own code
     *
     * @return the sum of the user time of all the threads
     */
    public long getSumUserTimeNano() {
        sumUserTimeNano = 0;
        for (TestReport report : getReports()) {
            sumUserTimeNano += report.getEndUserTimeNano();
        }
        return sumUserTimeNano;
    }

    /**
     * Returns the sum of the system time of all the threads in nanosec
     * System time is the time spent running OS code on behalf of your application (such as for I/O)
     *
     * @return the sum of the system time of all the threads in nanosec
     */
    public long getSumSystemTimeNano() {
        sumSystemTimeNano = 0;
        for (TestReport report : getReports()) {
            sumSystemTimeNano += report.getEndSystemTimeNano();
        }
        return sumSystemTimeNano;
    }

    /**
     * Returns the thread that runs in a given time in the past
     *
     * @param time that the returned thread run before it finishes
     * @return the thread that run in a given time
     */
    public SingleTestRunner getTestThreadFromTimeRun(long time) {
        SingleTestRunner testThread = null;
        long timeAggregation = 0;
        for (SingleTestRunner thread : threads) {
            timeAggregation += thread.getTestReport().getEndWallClockTime();
            if (timeAggregation <= time) {
                continue;
            } else {
                testThread = thread;
                break;
            }
        }
        return testThread;
    }

    /**
     * Adds a new thread
     *
     * @param thread
     */
    public void addThread(SingleTestRunner thread) {
        this.threads.add(thread);
    }

    /**
     * Returns a list with all the test reports
     *
     * @return a list with all the test reports
     */
    private ArrayList<TestReport> getReports() {
        ArrayList<TestReport> reports = new ArrayList<TestReport>();
        for (SingleTestRunner thread : threads) {
            reports.add(thread.getTestReport());
        }
        return reports;
    }

    /**
     * Returns a list with all the threads
     *
     * @return
     */
    public ArrayList<SingleTestRunner> getThreads() {
        return threads;
    }

}
