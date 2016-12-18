package rocks.appconcept.testing.perf;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by imeta on 16-Dec-16.
 */
public class SingleTestRunner implements Runnable {
    /** The test to invoke. */
    protected final PerfTest test;
    private TestReport testReport;

    /** Counter to track number of invocations. */
    protected final AtomicLong counter = new AtomicLong();

    /** Flag to indicate if test is alive or not. */
    protected final AtomicBoolean alive = new AtomicBoolean(true);

    protected final long id;

    /** Create a perf thread for the specified test instance. */
    public SingleTestRunner(long id, PerfTest test) {
        this.test = test;
        this.id = id;
    }

    /**
     * This is the main testing loop. The setup() method is called once and then the test method is
     * called in a tight loop until the test runner calls the stop() method, then the test tearDown()
     * method is invoked.
     */
    public void run() {
            //long now = System.currentTimeMillis();
            try {

                test.setUp();
                //this.testReport = new TestReport(id, this.getClass().getName());
                while (alive.get()) {
                    test.test(this.id);
                    counter.incrementAndGet();
                }
                //System.out.println(this.getClass().getName() + "#" + id + ", " + (System.currentTimeMillis() - now) + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    test.tearDown();
                    //this.testReport.stopTimers();

                    //testReport.printInfo();
                } catch (Exception e) {
                    // ignore errors in tearDown for now
                }
            }
    }

    /** Terminate the thread. */
    public void stop() {
        alive.set(false);
    }

    /** Get reference to the counter. */
    public AtomicLong getCounter() {
        return counter;
    }

    public long getId() {
        return id;
    }

    public TestReport getTestReport() {
        return this.testReport;
    }
}
